/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.audit.hook.listeners.util;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class Attribute {

	public Attribute(String name) {
		this(name, StringPool.BLANK, StringPool.BLANK);
	}

	public Attribute(String name, String newValue, String oldValue) {
		_name = name;
		_newValue = newValue;
		_oldValue = oldValue;
	}

	public String getName() {
		return _name;
	}

	public String getNewValue() {
		return _newValue;
	}

	public String getOldValue() {
		return _oldValue;
	}

	private String _name;
	private String _newValue;
	private String _oldValue;

}