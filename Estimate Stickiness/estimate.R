imagefile <- "C:\\Users\\IBM_ADMIN\\Documents\\My Work\\DLA\\images\\Stickiness_0_001.png"

estimateStickiness <- function(imageFile){
  
  library(imager)
  
  img <- load.image(imageFile)
  temp <- grayscale(as.cimg(img))
  
  imageData <- as.numeric(temp)
  d <- 1:nrow(temp)
  co <- merge(d,d)
  imageData <- data.frame(co,imageData)
  imageData <- subset(imageData,imageData==0)
  imageData$imageData <- NULL
  
  kc <- kmeans(imageData,1)
  
  value <- (sqrt((kc$tot.withinss)/nrow(imageData)))
  
  value <- -1.891781e-01 + (4.726054e-09 * (value)^4)+ (1.074950e-04 * (value)^2)+ (-1.316055e-06 * (value)^3)
  return(value)
}

estimateStickiness(imagefile)