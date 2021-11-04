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

package osp.icecap.sss.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import osp.icecap.sss.model.Term;

/**
 * The cache model class for representing Term in entity cache.
 *
 * @author Jerry H. Seo, Won Cheol Ryu
 * @generated
 */
public class TermCacheModel implements CacheModel<Term>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TermCacheModel)) {
			return false;
		}

		TermCacheModel termCacheModel = (TermCacheModel)obj;

		if (termId == termCacheModel.termId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, termId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", termId=");
		sb.append(termId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", name=");
		sb.append(name);
		sb.append(", version=");
		sb.append(version);
		sb.append(", type=");
		sb.append(type);
		sb.append(", displayName=");
		sb.append(displayName);
		sb.append(", definition=");
		sb.append(definition);
		sb.append(", tooltip=");
		sb.append(tooltip);
		sb.append(", synonyms=");
		sb.append(synonyms);
		sb.append(", standardized=");
		sb.append(standardized);
		sb.append(", attributesJSON=");
		sb.append(attributesJSON);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Term toEntityModel() {
		TermImpl termImpl = new TermImpl();

		if (uuid == null) {
			termImpl.setUuid("");
		}
		else {
			termImpl.setUuid(uuid);
		}

		termImpl.setTermId(termId);
		termImpl.setGroupId(groupId);
		termImpl.setCompanyId(companyId);
		termImpl.setUserId(userId);

		if (userName == null) {
			termImpl.setUserName("");
		}
		else {
			termImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			termImpl.setCreateDate(null);
		}
		else {
			termImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			termImpl.setModifiedDate(null);
		}
		else {
			termImpl.setModifiedDate(new Date(modifiedDate));
		}

		termImpl.setStatus(status);

		if (name == null) {
			termImpl.setName("");
		}
		else {
			termImpl.setName(name);
		}

		if (version == null) {
			termImpl.setVersion("");
		}
		else {
			termImpl.setVersion(version);
		}

		if (type == null) {
			termImpl.setType("");
		}
		else {
			termImpl.setType(type);
		}

		if (displayName == null) {
			termImpl.setDisplayName("");
		}
		else {
			termImpl.setDisplayName(displayName);
		}

		if (definition == null) {
			termImpl.setDefinition("");
		}
		else {
			termImpl.setDefinition(definition);
		}

		if (tooltip == null) {
			termImpl.setTooltip("");
		}
		else {
			termImpl.setTooltip(tooltip);
		}

		if (synonyms == null) {
			termImpl.setSynonyms("");
		}
		else {
			termImpl.setSynonyms(synonyms);
		}

		termImpl.setStandardized(standardized);

		if (attributesJSON == null) {
			termImpl.setAttributesJSON("");
		}
		else {
			termImpl.setAttributesJSON(attributesJSON);
		}

		termImpl.resetOriginalValues();

		return termImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		termId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		status = objectInput.readInt();
		name = objectInput.readUTF();
		version = objectInput.readUTF();
		type = objectInput.readUTF();
		displayName = objectInput.readUTF();
		definition = objectInput.readUTF();
		tooltip = objectInput.readUTF();
		synonyms = objectInput.readUTF();

		standardized = objectInput.readBoolean();
		attributesJSON = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(termId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeInt(status);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (displayName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(displayName);
		}

		if (definition == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(definition);
		}

		if (tooltip == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(tooltip);
		}

		if (synonyms == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(synonyms);
		}

		objectOutput.writeBoolean(standardized);

		if (attributesJSON == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(attributesJSON);
		}
	}

	public String uuid;
	public long termId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int status;
	public String name;
	public String version;
	public String type;
	public String displayName;
	public String definition;
	public String tooltip;
	public String synonyms;
	public boolean standardized;
	public String attributesJSON;

}