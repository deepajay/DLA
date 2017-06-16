path <- "C:\\Users\\IBM_ADMIN\\Documents\\My Work\\DLA\\images\\"

images <- list.files(path)

library(imager)

data <- data.frame(images=images,Metric=1)

for(i in 1:nrow(data)){
  img <- load.image(paste(path,images[i],sep = ""))
  temp <- grayscale(as.cimg(img))
  
  imageData <- as.numeric(temp)
  d <- 1:nrow(temp)
  co <- merge(d,d)
  imageData <- data.frame(co,imageData)
  imageData <- subset(imageData,imageData==0)
  imageData$imageData <- NULL
  
  kc <- kmeans(imageData,1)
  
  data$Metric[i] <- kc$tot.withinss
}

data$stick <- gsub(data$images,pattern = "Stickiness_",replacement = "")
data$stick <- gsub(data$stick,pattern = ".png",replacement = "")
data$stick <- as.numeric(gsub(data$stick,pattern = "_",replacement = "."))


ss <- function(x) sum(scale(x, scale = FALSE)^2)
sqrt(ss(imageData)/nrow(imageData))/(sqrt(nrow(co))/2)
