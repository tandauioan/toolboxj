package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.self.SelfConditionalConsumer;
import org.ticdev.toolboxj.self.SelfConsumer;
import org.ticdev.toolboxj.self.SelfFilter;
import org.ticdev.toolboxj.self.SelfMapper;

/**
 * Basic tuple interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            tuple type
 */
public interface Tuple<T extends Tuple<T>>
    extends
    SelfFilter<T>,
    SelfMapper<T>,
    SelfConsumer<T>,
    SelfConditionalConsumer<T> {

}
