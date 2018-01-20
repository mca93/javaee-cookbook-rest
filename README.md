# Build
mvn clean package && docker build -t com.pedantic/jeecb-rest .

# RUN

docker rm -f jeecb-rest || true && docker run -d -p 8080:8080 -p 4848:4848 --name jeecb-rest com.pedantic/jeecb-rest 