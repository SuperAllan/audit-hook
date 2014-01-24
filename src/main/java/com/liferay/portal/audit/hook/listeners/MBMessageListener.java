package com.liferay.portal.audit.hook.listeners;

import com.liferay.portal.audit.hook.listeners.util.Attribute;
import com.liferay.portal.audit.hook.listeners.util.AttributesBuilder;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;

public class MBMessageListener extends AbstractListener<MBMessage> {

	@Override
	protected List<Attribute> getModifiedAttributes(
		final MBMessage newMBMessage, 
		final MBMessage oldMBMessage) {

		final AttributesBuilder attributesBuilder = 
			new AttributesBuilder(
				newMBMessage, oldMBMessage);

		attributesBuilder.add("classNameId");
		attributesBuilder.add("classPK");
		attributesBuilder.add("categoryId");
		attributesBuilder.add("threadId");
		attributesBuilder.add("rootMessageId");
		attributesBuilder.add("parentMessageId");
		attributesBuilder.add("subject");
		attributesBuilder.add("body");
		attributesBuilder.add("format");
		attributesBuilder.add("attachments");
		attributesBuilder.add("anonymous");
		attributesBuilder.add("priority");
		attributesBuilder.add("allowPingbacks");
		attributesBuilder.add("answer");
		attributesBuilder.add("status");
		attributesBuilder.add("statusByUserId");
		attributesBuilder.add("statusByUserName");
		attributesBuilder.add("statusDate");

		return attributesBuilder.getAttributes();

	}

	@Override
	protected MBMessage getOldModel(final long id) throws SystemException {
		return MBMessageLocalServiceUtil.fetchMBMessage(id);
	}

	@Override
	protected long getPrimaryKeyObj(final MBMessage model) {
		return model.getMessageId();
	}

}
