import cv2
import numpy as np
import matplotlib.pyplot as plt
from skimage.morphology import binary_erosion, binary_opening, square

# Read the image
img = cv2.imread('p.png', 0)  # Read as grayscale (0)

# Flip the image vertically
img_flipped = np.flipud(img)

# Plot the flipped original image
plt.imshow(img_flipped, cmap='gray')
plt.title('Flipped Original Image')
plt.colorbar()  # Optional color bar
plt.show()

# Thresholding to binary image
ret, bw_img = cv2.threshold(img, 128, 255, cv2.THRESH_BINARY)

# Change the image to binary form (255 for black, 0 for white)
bw_img = cv2.threshold(img, 128, 255, cv2.THRESH_BINARY)[1]

# Perform binary opening to smooth the image
kernel = square(17)
bw_img = binary_opening(bw_img, kernel)

# Perform binary erosion to enlarge black areas (obstacles)
bw_img = binary_erosion(bw_img, kernel)

# Display the processed image
plt.imshow(bw_img, cmap='gray')
plt.title('Processed Image')
plt.colorbar()  # Optional color bar

# Reverse the y-axis to label from bottom to top

# Example: Plot a point at (100, 200)
plt.scatter([401], [400], color='red', marker='x', label='Point (401, 400)')
plt.legend()

plt.show()

# Convert processed image to obstacle map (1 for obstacles, 0 for open space)
new_img = np.where(bw_img == 0, 1, 0)

# Save image array to text file
np.savetxt("mapArray.txt", new_img, delimiter=",", fmt='%d')

# Read the saved mapArray.txt to verify
imgArr = np.loadtxt("mapArray.txt", delimiter=",").astype(int)
print("Shape of loaded array:", imgArr.shape)

