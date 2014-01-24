package com.liferay.portal.audit.hook.listeners;

import java.util.List;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.audit.hook.listeners.util.Attribute;
import com.liferay.portal.audit.hook.listeners.util.AuditMessageBuilder;
import com.liferay.portal.audit.util.EventTypes;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.ModelListener;

public abstract class AbstractListener<T> 
	implements ModelListener<T> {

	public AbstractListener() {
		if (log.isInfoEnabled()) {
			log.info("Listener registred!");
		}
	}

	public void onAfterAddAssociation(
		final Object classPK,
		final String associationClassName,
		final Object associationClassPK)
			throws ModelListenerException {
	}

	public void onAfterCreate(final T model) throws ModelListenerException {
		auditMessage(EventTypes.ADD, model);
		if (log.isDebugEnabled()) {
			log.debug("Audit on create: " + model);
		}
	}

	public void onAfterRemove(final T model) throws ModelListenerException {
		auditMessage(EventTypes.DELETE, model);
		if (log.isDebugEnabled()) {
			log.debug("Audit on delete: " + model);
		}
	}

	public void onAfterRemoveAssociation(
		final Object classPK,
		final String associationClassName,
		final Object associationClassPK)
			throws ModelListenerException {
	}

	public void onAfterUpdate(final T model) throws ModelListenerException {
		auditMessage(EventTypes.UPDATE, model);
		if (log.isDebugEnabled()) {
			log.debug("Audit on update: " + model);
		}
	}

	public void onBeforeAddAssociation(
		final Object classPK,
		final String associationClassName,
		final Object associationClassPK)
			throws ModelListenerException {
	}

	public void onBeforeCreate(final T model) throws ModelListenerException {
	}

	public void onBeforeRemove(final T model) throws ModelListenerException {
	}

	public void onBeforeRemoveAssociation(
		final Object classPK,
		final String associationClassName,
		final Object associationClassPK)
			throws ModelListenerException {
	}

	public void onBeforeUpdate(final T model) throws ModelListenerException {
	}

	protected final void auditMessage(
		final String eventType, final T model)
			throws ModelListenerException {

		try {

			final long classPK = getPrimaryKeyObj(model);
			final T oldModel = getOldModel(classPK);

			final List<Attribute> attributes = getModifiedAttributes(
					model, oldModel);

			final AuditMessage auditMessage =
					AuditMessageBuilder.buildAuditMessage(
							eventType, _getQualifiedNameInterface(model),
							classPK, attributes);

			copyPropertiesToAuditMessage(auditMessage, model);

			AuditRouterUtil.route(auditMessage);

			if (log.isDebugEnabled()) {
				log.debug("Audit message: "
					+ auditMessage.toJSONObject().toString());
			}

		}
		catch (final Exception e) {
			throw new ModelListenerException(e);
		}

	}

	protected void copyPropertiesToAuditMessage(
		final AuditMessage auditMessage, final T model) {

		copyPropertyToAuditMessage(auditMessage, model, "userId");
		copyPropertyToAuditMessage(auditMessage, model, "userName");
		copyPropertyToAuditMessage(auditMessage, model, "userMessageId");
		copyPropertyToAuditMessage(auditMessage, model, "userCompanyId");

	}

	protected final void copyPropertyToAuditMessage(
		final AuditMessage auditMessage, final T model, final String property) {

		final Object value =
			BeanPropertiesUtil.getObjectSilent(model, property, null);

		if (value != null) {
			BeanPropertiesUtil.setProperty(auditMessage, property, value);
		}

	}

	protected abstract List<Attribute> getModifiedAttributes(
			final T newModel, 
			final T oldModel);

	protected abstract long getPrimaryKeyObj(final T model);

	protected abstract T getOldModel(final long id) throws SystemException;

	private String _getQualifiedNameInterface(final T model) {
		return model.getClass().getSuperclass().getInterfaces()[0].getName();
	}

	protected static Log log = LogFactoryUtil.getLog(AbstractListener.class);

}
