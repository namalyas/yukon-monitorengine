package lk.yukon.servicemonitor.listener;

import lk.yukon.servicemonitor.model.Service;
import org.apache.log4j.Logger;

/**
 * license: BSD - see LICENSE for details
 *
 * ServiceBehaviourAdapter is used as Adapter to ServiceBehaviourListener. if developer not wants to listen all the events then its easy to used this one instead of listener
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public abstract class ServiceBehaviourAdapter implements ServiceBehaviourListener{

    private static final Logger LOGGER= Logger.getLogger(ServiceBehaviourAdapter.class);
    public void serviceUp(Service service) {
        LOGGER.info(String.format("Calling method - serviceUp(%s)",service));

    }

    public void serviceDown(Service service) {
        LOGGER.info(String.format("Calling method - serviceDown(%s)",service));

    }
}
