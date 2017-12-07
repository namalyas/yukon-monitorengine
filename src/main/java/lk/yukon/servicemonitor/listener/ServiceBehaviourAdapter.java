package lk.yukon.servicemonitor.listener;

import lk.yukon.servicemonitor.model.Service;

/**
 * license: BSD - see LICENSE for details
 *
 * ServiceBehaviourAdapter is used as Adapter to ServiceBehaviourListener. if developer not wants to listen all the events then its easy to used this one instead of listener
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public abstract class ServiceBehaviourAdapter implements ServiceBehaviourListener{

    public void serviceUp(Service service) {

    }

    public void serviceDown(Service service) {

    }
}
