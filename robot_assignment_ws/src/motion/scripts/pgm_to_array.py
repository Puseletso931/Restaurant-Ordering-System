#!/usr/bin/env python
import yaml
import matplotlib.pyplot as plt
import numpy as np

def read_pgm_file(pgm_file):
    with open(pgm_file, 'rb') as f:
        # Read header
        header = f.readline().decode().strip()
        if header != 'P5':
            raise ValueError("File is not a P5 PGM file.")

        # Read comments
        while True:
            line = f.readline().decode().strip()
            if not line.startswith('#'):
                break

        # Read width and height
        width, height = map(int, line.split())

        # Read max grayscale value
        max_val = int(f.readline().decode().strip())

        # Read image data
        img_data = np.fromfile(f, dtype=np.uint8, count=width * height)

    return width, height, img_data

def main(yaml_file, pgm_file,output_file):
    # Read YAML file
    with open(yaml_file, 'r') as f:
        yaml_data = yaml.safe_load(f)
        resolution = yaml_data['resolution']
        origin = yaml_data['origin']
        image_filename = yaml_data['image']

    # Read PGM file
    width, height, img_data = read_pgm_file(pgm_file)
    
    # Reshape image data into a 2D array
    occupancy_grid = np.reshape(img_data, (height, width))
   # Save the occupancy grid data as binary row by row
    with open(output_file, 'wb') as f:
        for row in occupancy_grid:
            f.write(row.tobytes())

    print("Occupancy grid data saved to",output_file)

    # Visualize the occupancy grid
    plt.figure(figsize=(8, 6))
    plt.imshow(occupancy_grid, cmap='gray', origin='lower')
    plt.colorbar()
    plt.title('Occupancy Grid')
    plt.xlabel('X')
    plt.ylabel('Y')
    plt.show()

if __name__ == '__main__':
    yaml_file = '/home/puseletso/ros_home/robot_assignment_ws/p.yaml'
    pgm_file = '/home/puseletso/ros_home/robot_assignment_ws/p.pgm'
    output_file = 'mapArray.txt'
    main(yaml_file, pgm_file,output_file)
