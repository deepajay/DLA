
src/Simulate.java : Code to generate DLA
Estimate Stickiness/analysis.R : playing around with image data
Estimate Stickiness/estimate.R : code to predict the stickiness value
images folder: set of generated DLA images
estimatedStickiness.csv : Result of estimation on generated images

Approach for stickiness estimation:
It can be observed that with increase in the value of stickiness the density of particles in the images gets reduced. If we imagine each image as a single cluster of particles then this stickiness appears to be inversly proportional to the tightness of the cluster.

Total withiness of a cluster is a metric to compare the tightness of a cluster. It is the sum of squares of distance of each data point from its cluster centre. Since this value can be biased wrt to the number of particles considered, a better metric would the mean distance from the cluster centre. So the total withiness is divide by number of particles and a sqaure root is done (as it is sum of squares).

When I plotted the stickiness values on y axis and the computed value on x axis, I observed that polynomial curve fitting may solve the problem.

Hence, whenever a image comes up, its value is computed(based on above mentioned approach) and using weights of the fitted polynomial the stickiness is predicted.