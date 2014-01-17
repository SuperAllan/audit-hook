package com.liferay.portal.audit.hook.listeners;

import java.util.List;

import com.liferay.portal.audit.hook.listeners.util.Attribute;
import com.liferay.portal.audit.hook.listeners.util.AttributesBuilder;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

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
	protected void configureMessage(final AuditMessage auditMessage,
		final MBMessage newModel) {

		auditMessage.setUserId(newModel.getUserId());
		auditMessage.setUserName(newModel.getUserName());
		auditMessage.setClassPK(newModel.getMessageId());
		auditMessage.setCompanyId(newModel.getCompanyId());

	}

	@Override
	protected MBMessage getOldModel(long id) throws SystemException {
		return MBMessageLocalServiceUtil.fetchMBMessage(id);
	}


}
