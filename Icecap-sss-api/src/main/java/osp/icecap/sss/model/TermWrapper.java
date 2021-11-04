/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package osp.icecap.sss.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Term}.
 * </p>
 *
 * @author Jerry H. Seo, Won Cheol Ryu
 * @see Term
 * @generated
 */
public class TermWrapper
	extends BaseModelWrapper<Term> implements ModelWrapper<Term>, Term {

	public TermWrapper(Term term) {
		super(term);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("termId", getTermId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("status", getStatus());
		attributes.put("name", getName());
		attributes.put("version", getVersion());
		attributes.put("type", getType());
		attributes.put("displayName", getDisplayName());
		attributes.put("definition", getDefinition());
		attributes.put("tooltip", getTooltip());
		attributes.put("synonyms", getSynonyms());
		attributes.put("standardized", isStandardized());
		attributes.put("attributesJSON", getAttributesJSON());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long termId = (Long)attributes.get("termId");

		if (termId != null) {
			setTermId(termId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String displayName = (String)attributes.get("displayName");

		if (displayName != null) {
			setDisplayName(displayName);
		}

		String definition = (String)attributes.get("definition");

		if (definition != null) {
			setDefinition(definition);
		}

		String tooltip = (String)attributes.get("tooltip");

		if (tooltip != null) {
			setTooltip(tooltip);
		}

		String synonyms = (String)attributes.get("synonyms");

		if (synonyms != null) {
			setSynonyms(synonyms);
		}

		Boolean standardized = (Boolean)attributes.get("standardized");

		if (standardized != null) {
			setStandardized(standardized);
		}

		String attributesJSON = (String)attributes.get("attributesJSON");

		if (attributesJSON != null) {
			setAttributesJSON(attributesJSON);
		}
	}

	/**
	 * Returns the attributes json of this term.
	 *
	 * @return the attributes json of this term
	 */
	@Override
	public String getAttributesJSON() {
		return model.getAttributesJSON();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getAttributesJSONObject()
		throws com.liferay.portal.kernel.json.JSONException {

		return model.getAttributesJSONObject();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this term.
	 *
	 * @return the company ID of this term
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this term.
	 *
	 * @return the create date of this term
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the definition of this term.
	 *
	 * @return the definition of this term
	 */
	@Override
	public String getDefinition() {
		return model.getDefinition();
	}

	/**
	 * Returns the localized definition of this term in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized definition of this term
	 */
	@Override
	public String getDefinition(java.util.Locale locale) {
		return model.getDefinition(locale);
	}

	/**
	 * Returns the localized definition of this term in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized definition of this term. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDefinition(java.util.Locale locale, boolean useDefault) {
		return model.getDefinition(locale, useDefault);
	}

	/**
	 * Returns the localized definition of this term in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized definition of this term
	 */
	@Override
	public String getDefinition(String languageId) {
		return model.getDefinition(languageId);
	}

	/**
	 * Returns the localized definition of this term in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized definition of this term
	 */
	@Override
	public String getDefinition(String languageId, boolean useDefault) {
		return model.getDefinition(languageId, useDefault);
	}

	@Override
	public String getDefinitionCurrentLanguageId() {
		return model.getDefinitionCurrentLanguageId();
	}

	@Override
	public String getDefinitionCurrentValue() {
		return model.getDefinitionCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized definitions of this term.
	 *
	 * @return the locales and localized definitions of this term
	 */
	@Override
	public Map<java.util.Locale, String> getDefinitionMap() {
		return model.getDefinitionMap();
	}

	/**
	 * Returns the display name of this term.
	 *
	 * @return the display name of this term
	 */
	@Override
	public String getDisplayName() {
		return model.getDisplayName();
	}

	/**
	 * Returns the localized display name of this term in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized display name of this term
	 */
	@Override
	public String getDisplayName(java.util.Locale locale) {
		return model.getDisplayName(locale);
	}

	/**
	 * Returns the localized display name of this term in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized display name of this term. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDisplayName(java.util.Locale locale, boolean useDefault) {
		return model.getDisplayName(locale, useDefault);
	}

	/**
	 * Returns the localized display name of this term in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized display name of this term
	 */
	@Override
	public String getDisplayName(String languageId) {
		return model.getDisplayName(languageId);
	}

	/**
	 * Returns the localized display name of this term in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized display name of this term
	 */
	@Override
	public String getDisplayName(String languageId, boolean useDefault) {
		return model.getDisplayName(languageId, useDefault);
	}

	@Override
	public String getDisplayNameCurrentLanguageId() {
		return model.getDisplayNameCurrentLanguageId();
	}

	@Override
	public String getDisplayNameCurrentValue() {
		return model.getDisplayNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized display names of this term.
	 *
	 * @return the locales and localized display names of this term
	 */
	@Override
	public Map<java.util.Locale, String> getDisplayNameMap() {
		return model.getDisplayNameMap();
	}

	@Override
	public String getDisplayTitle(java.util.Locale locale) {
		return model.getDisplayTitle(locale);
	}

	/**
	 * Returns the group ID of this term.
	 *
	 * @return the group ID of this term
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this term.
	 *
	 * @return the modified date of this term
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this term.
	 *
	 * @return the name of this term
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this term.
	 *
	 * @return the primary key of this term
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the standardized of this term.
	 *
	 * @return the standardized of this term
	 */
	@Override
	public boolean getStandardized() {
		return model.getStandardized();
	}

	/**
	 * Returns the status of this term.
	 *
	 * @return the status of this term
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the synonyms of this term.
	 *
	 * @return the synonyms of this term
	 */
	@Override
	public String getSynonyms() {
		return model.getSynonyms();
	}

	/**
	 * Returns the term ID of this term.
	 *
	 * @return the term ID of this term
	 */
	@Override
	public long getTermId() {
		return model.getTermId();
	}

	/**
	 * Returns the tooltip of this term.
	 *
	 * @return the tooltip of this term
	 */
	@Override
	public String getTooltip() {
		return model.getTooltip();
	}

	/**
	 * Returns the localized tooltip of this term in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized tooltip of this term
	 */
	@Override
	public String getTooltip(java.util.Locale locale) {
		return model.getTooltip(locale);
	}

	/**
	 * Returns the localized tooltip of this term in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized tooltip of this term. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getTooltip(java.util.Locale locale, boolean useDefault) {
		return model.getTooltip(locale, useDefault);
	}

	/**
	 * Returns the localized tooltip of this term in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized tooltip of this term
	 */
	@Override
	public String getTooltip(String languageId) {
		return model.getTooltip(languageId);
	}

	/**
	 * Returns the localized tooltip of this term in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized tooltip of this term
	 */
	@Override
	public String getTooltip(String languageId, boolean useDefault) {
		return model.getTooltip(languageId, useDefault);
	}

	@Override
	public String getTooltipCurrentLanguageId() {
		return model.getTooltipCurrentLanguageId();
	}

	@Override
	public String getTooltipCurrentValue() {
		return model.getTooltipCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized tooltips of this term.
	 *
	 * @return the locales and localized tooltips of this term
	 */
	@Override
	public Map<java.util.Locale, String> getTooltipMap() {
		return model.getTooltipMap();
	}

	/**
	 * Returns the type of this term.
	 *
	 * @return the type of this term
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this term.
	 *
	 * @return the user ID of this term
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this term.
	 *
	 * @return the user name of this term
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this term.
	 *
	 * @return the user uuid of this term
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this term.
	 *
	 * @return the uuid of this term
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this term.
	 *
	 * @return the version of this term
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns <code>true</code> if this term is standardized.
	 *
	 * @return <code>true</code> if this term is standardized; <code>false</code> otherwise
	 */
	@Override
	public boolean isStandardized() {
		return model.isStandardized();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets the attributes json of this term.
	 *
	 * @param attributesJSON the attributes json of this term
	 */
	@Override
	public void setAttributesJSON(String attributesJSON) {
		model.setAttributesJSON(attributesJSON);
	}

	/**
	 * Sets the company ID of this term.
	 *
	 * @param companyId the company ID of this term
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this term.
	 *
	 * @param createDate the create date of this term
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the definition of this term.
	 *
	 * @param definition the definition of this term
	 */
	@Override
	public void setDefinition(String definition) {
		model.setDefinition(definition);
	}

	/**
	 * Sets the localized definition of this term in the language.
	 *
	 * @param definition the localized definition of this term
	 * @param locale the locale of the language
	 */
	@Override
	public void setDefinition(String definition, java.util.Locale locale) {
		model.setDefinition(definition, locale);
	}

	/**
	 * Sets the localized definition of this term in the language, and sets the default locale.
	 *
	 * @param definition the localized definition of this term
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDefinition(
		String definition, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDefinition(definition, locale, defaultLocale);
	}

	@Override
	public void setDefinitionCurrentLanguageId(String languageId) {
		model.setDefinitionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized definitions of this term from the map of locales and localized definitions.
	 *
	 * @param definitionMap the locales and localized definitions of this term
	 */
	@Override
	public void setDefinitionMap(Map<java.util.Locale, String> definitionMap) {
		model.setDefinitionMap(definitionMap);
	}

	/**
	 * Sets the localized definitions of this term from the map of locales and localized definitions, and sets the default locale.
	 *
	 * @param definitionMap the locales and localized definitions of this term
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDefinitionMap(
		Map<java.util.Locale, String> definitionMap,
		java.util.Locale defaultLocale) {

		model.setDefinitionMap(definitionMap, defaultLocale);
	}

	/**
	 * Sets the display name of this term.
	 *
	 * @param displayName the display name of this term
	 */
	@Override
	public void setDisplayName(String displayName) {
		model.setDisplayName(displayName);
	}

	/**
	 * Sets the localized display name of this term in the language.
	 *
	 * @param displayName the localized display name of this term
	 * @param locale the locale of the language
	 */
	@Override
	public void setDisplayName(String displayName, java.util.Locale locale) {
		model.setDisplayName(displayName, locale);
	}

	/**
	 * Sets the localized display name of this term in the language, and sets the default locale.
	 *
	 * @param displayName the localized display name of this term
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDisplayName(
		String displayName, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDisplayName(displayName, locale, defaultLocale);
	}

	@Override
	public void setDisplayNameCurrentLanguageId(String languageId) {
		model.setDisplayNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized display names of this term from the map of locales and localized display names.
	 *
	 * @param displayNameMap the locales and localized display names of this term
	 */
	@Override
	public void setDisplayNameMap(
		Map<java.util.Locale, String> displayNameMap) {

		model.setDisplayNameMap(displayNameMap);
	}

	/**
	 * Sets the localized display names of this term from the map of locales and localized display names, and sets the default locale.
	 *
	 * @param displayNameMap the locales and localized display names of this term
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDisplayNameMap(
		Map<java.util.Locale, String> displayNameMap,
		java.util.Locale defaultLocale) {

		model.setDisplayNameMap(displayNameMap, defaultLocale);
	}

	/**
	 * Sets the group ID of this term.
	 *
	 * @param groupId the group ID of this term
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this term.
	 *
	 * @param modifiedDate the modified date of this term
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this term.
	 *
	 * @param name the name of this term
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this term.
	 *
	 * @param primaryKey the primary key of this term
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this term is standardized.
	 *
	 * @param standardized the standardized of this term
	 */
	@Override
	public void setStandardized(boolean standardized) {
		model.setStandardized(standardized);
	}

	/**
	 * Sets the status of this term.
	 *
	 * @param status the status of this term
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the synonyms of this term.
	 *
	 * @param synonyms the synonyms of this term
	 */
	@Override
	public void setSynonyms(String synonyms) {
		model.setSynonyms(synonyms);
	}

	/**
	 * Sets the term ID of this term.
	 *
	 * @param termId the term ID of this term
	 */
	@Override
	public void setTermId(long termId) {
		model.setTermId(termId);
	}

	/**
	 * Sets the tooltip of this term.
	 *
	 * @param tooltip the tooltip of this term
	 */
	@Override
	public void setTooltip(String tooltip) {
		model.setTooltip(tooltip);
	}

	/**
	 * Sets the localized tooltip of this term in the language.
	 *
	 * @param tooltip the localized tooltip of this term
	 * @param locale the locale of the language
	 */
	@Override
	public void setTooltip(String tooltip, java.util.Locale locale) {
		model.setTooltip(tooltip, locale);
	}

	/**
	 * Sets the localized tooltip of this term in the language, and sets the default locale.
	 *
	 * @param tooltip the localized tooltip of this term
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTooltip(
		String tooltip, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setTooltip(tooltip, locale, defaultLocale);
	}

	@Override
	public void setTooltipCurrentLanguageId(String languageId) {
		model.setTooltipCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized tooltips of this term from the map of locales and localized tooltips.
	 *
	 * @param tooltipMap the locales and localized tooltips of this term
	 */
	@Override
	public void setTooltipMap(Map<java.util.Locale, String> tooltipMap) {
		model.setTooltipMap(tooltipMap);
	}

	/**
	 * Sets the localized tooltips of this term from the map of locales and localized tooltips, and sets the default locale.
	 *
	 * @param tooltipMap the locales and localized tooltips of this term
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTooltipMap(
		Map<java.util.Locale, String> tooltipMap,
		java.util.Locale defaultLocale) {

		model.setTooltipMap(tooltipMap, defaultLocale);
	}

	/**
	 * Sets the type of this term.
	 *
	 * @param type the type of this term
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this term.
	 *
	 * @param userId the user ID of this term
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this term.
	 *
	 * @param userName the user name of this term
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this term.
	 *
	 * @param userUuid the user uuid of this term
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this term.
	 *
	 * @param uuid the uuid of this term
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this term.
	 *
	 * @param version the version of this term
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected TermWrapper wrap(Term term) {
		return new TermWrapper(term);
	}

}