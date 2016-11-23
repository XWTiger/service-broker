package com.chinacloud.isv.component;

import com.chinacloud.isv.entity.ValueProvider;

public interface EventDriver {
	/**
	 * launch a event
	 * @param ValueProvider
	 */
	public String go(ValueProvider valueProvider);
	public void close();
}
