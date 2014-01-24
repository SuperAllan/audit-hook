package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.audit.hook.listeners.util.AuditMessageBuilder;
import com.liferay.portal.audit.util.EventTypes;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceWrapper;

public class AssetEntryLocalServiceWrapperImpl extends AssetEntryLocalServiceWrapper {

	public AssetEntryLocalServiceWrapperImpl(
			final AssetEntryLocalService assetEntryLocalService) {
		super(assetEntryLocalService);
	}

	@Override
	public AssetEntry incrementViewCounter(
			long userId, String className, long classPK)
		throws PortalException, SystemException {

		final AssetEntry assetEntry =
			super.incrementViewCounter(userId, className, classPK);

		auditView(userId, className, classPK);

		return assetEntry;

	}

	protected void auditView(long userId, String className, long classPK)
		throws PortalException, SystemException {

		final AuditMessage auditMessage =
			AuditMessageBuilder.buildAuditMessage(
					EventTypes.VIEW, className,
					classPK, null);

		final User user = UserLocalServiceUtil.getUser(userId);

		auditMessage.setUserId(user.getUserId());
		auditMessage.setUserName(user.getFullName());
		auditMessage.setCompanyId(user.getCompanyId());

		AuditRouterUtil.route(auditMessage);
	}

}
