path <- "C:\\Users\\IBM_ADMIN\\Documents\\My Work\\DLA\\images\\"

images <- list.files(path)

estimate <- function(imageFile){
  
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
  
  return(kc$tot.withinss)
}

data <- data.frame(images=images,Metric=1)

for(i in 1:nrow(data)){
  data$Metric[i] <- estimate(paste(path,data$images[i],sep = ""))
}

data$stick <- gsub(data$images,pattern = "Stickiness_",replacement = "")
data$stick <- gsub(data$stick,pattern = ".png",replacement = "")
data$stick <- as.numeric(gsub(data$stick,pattern = "_",replacement = "."))

#Average distance of each point from the mean coordinate
data$value <- sqrt((data$Metric)/(20001))

plot(y = data$stick,x = data$value)
#The Graph shows that the relationship between stickiness and the value is polynomial

#Training a linear regression to estimate stickiness
fit <- lm(stick ~ I(value^4)+I(value^2)+I(value^3),data)

#(Intercept)    I(value^4)    I(value^2)    I(value^3) 
#-1.891781e-01  4.726054e-09  1.074950e-04 -1.316055e-06 

data$predicted.Stickiness <- predict.lm(fit,data)

plot(y = data$stick,x = data$value)
lines(data$value,predict(fit,data),col="red",lty=2,lwd=3)

write.csv(data,"C:\\Users\\IBM_ADMIN\\Documents\\My Work\\DLA\\estimatedStickiness.csv",row.names = FALSE)
