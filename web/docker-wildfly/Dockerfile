# adaptado de https://github.com/goldmann/wildfly-docker-configuration/tree/master/cli

FROM jboss/wildfly:latest

ADD deployments /opt/jboss/wildfly/standalone/deployments/

ADD custom /opt/jboss/wildfly/custom/

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin
RUN /opt/jboss/wildfly/custom/execute.sh

RUN rm -rf /opt/jboss/wildfly/standalone/configuration/standalone_xml_history/current

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]