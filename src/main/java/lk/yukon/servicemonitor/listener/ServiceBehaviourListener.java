package lk.yukon.servicemonitor.listener;

import lk.yukon.servicemonitor.model.Service;

/**
 * license: BSD - see LICENSE for details
 *
 * ServiceBehaviourListener is listen to the service behaviour changes
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public interface ServiceBehaviourListener {

    public void serviceUp(Service service);
    public void serviceDown(Service service);
    
}
