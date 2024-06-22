#!/usr/bin/env python
import rospy
from gazebo_msgs.srv import GetModelState
from geometry_msgs.msg import Twist
from turtlesim.msg import Pose
from math import atan2
from gazebo_msgs.msg import ModelStates
from tf.transformations import euler_from_quaternion
from pid import PID
import prm


class TurtleBot:
    def __init__(self):
        # create the robot controller node
        rospy.init_node('turtlebot_controller', anonymous=True)

        self.velocity_publisher = rospy.Publisher('/cmd_vel_mux/input/navi',
                                                  Twist, queue_size=10)

        # a subscriber to update the robot position
        self.pose_subscriber = rospy.Subscriber("/gazebo/model_states", ModelStates, self.update_position)

        self.pose = Pose()
        self.rate = rospy.Rate(10)

    def update_position(self, msg):
        index = 0
        for i in range(len(msg.name)):
            if msg.name[i] == 'mobile_base':
                index = i
                break

        x =round( msg.pose[index].position.x,5)
        y = round(msg.pose[index].position.y,5)
        rot_q = msg.pose[index].orientation

        _, _, theta = euler_from_quaternion([rot_q.x, rot_q.y, rot_q.z, rot_q.w])
        
        self.pose.x = x
        self.pose.y = y
        self.pose.theta = theta

    @staticmethod
    def get_current_state():
        rospy.wait_for_service('/gazebo/get_model_state')
        try:
            gms = rospy.ServiceProxy('/gazebo/get_model_state', GetModelState)
            state = gms(model_name="mobile_base")
            return state
        except rospy.ServiceException as e:
            print('Service call failed: ' + str(e)) 

    def steering_angle(self, state, goal_position):
        return atan2(goal_position.y - state.pose.position.y, goal_position.x - state.pose.position.x)

    def angular_vel(self, state, theta, goal_pose, constant=3):
        ang_vel   = constant * (self.steering_angle(state, goal_pose) - theta)
        max_vel = 10
        if abs(ang_vel) < max_vel:
            return ang_vel
        else:
            if ang_vel < 0:
                return -max_vel
            else:
                return max_vel

    def steer(self, goal_position):
        vel_msg = Twist()

        while not rospy.is_shutdown():
            pid_controller = PID()
            state = self.get_current_state()
            theta = pid_controller.get_rotation(state)
            
            pid_dist = pid_controller.compute_pid(pid_controller.get_euclidean_distance(state, goal_position))

            vel_msg.linear.x = pid_dist

            vel_msg.angular.x = 0
            vel_msg.angular.y = 0
            vel_msg.angular.z =  self.angular_vel(state, theta, goal_position)

            # Publishing our vel_msg
            self.velocity_publisher.publish(vel_msg)

            if (pid_controller.get_euclidean_distance(state, goal_position) < 0.05):
                # the goal has been found
                break

    def follow_path(self, path):
        goal_pose = Pose()
        for step in path :
            goal_pose.x = step[0]
            goal_pose.y = step[1]
            
            self.steer(goal_pose)

if __name__ == '__main__':
    try:
        path = prm.main()
        path = path[::-1]
        x = TurtleBot()
        x.follow_path(path)

    except rospy.ROSInterruptException:
        pass
