FROM airhacks/glassfish
COPY ./target/jeecb-rest.war ${DEPLOYMENT_DIR}
