FROM openjdk:17-jdk-alpine
RUN mkdir /products
WORKDIR /products
COPY target/*jar /products/product.jar
CMD ["java", "-jar", "/products/product.jar"]
