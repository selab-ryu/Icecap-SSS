package osp.icecap.sss.web.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.trash.TrashHelper;

import java.util.List;
import java.util.Locale;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.web.security.permission.resource.TermModelPermission;
import osp.icecap.sss.web.util.TermActionDropdownItemsProvider;

public class TermVerticalCard extends BaseVerticalCard {
	public TermVerticalCard(
			Term term, 
			RenderRequest renderRequest,
			RenderResponse renderResponse,
			RowChecker rowChecker,
			TrashHelper trashHelper, 
			String termURL,
			PermissionChecker permissionChecker) {

			super(term, renderRequest, rowChecker);

			_term = term;
			_renderRequest = renderRequest;
			_renderResponse = renderResponse;
			_locale = renderRequest.getLocale();
			_trashHelper = trashHelper;
			_termURL = termURL;
			_permissionChecker = permissionChecker;
		}

		@Override
		public List<DropdownItem> getActionDropdownItems() {
				TermActionDropdownItemsProvider
					termActionDropdownItemsProvider =
						new TermActionDropdownItemsProvider(
							_term, renderRequest, _renderResponse,
							_permissionChecker, _trashHelper);

				return termActionDropdownItemsProvider.
					getActionDropdownItems();
		}

		public String getAspectRatioCssClasses() {
			return "aspect-ratio-item-center-middle " +
				"aspect-ratio-item-vertical-fluid";
		}

		@Override
		public String getDefaultEventHandler() {
			return IcecapSSSConstants.TERM_ELEMENTS_DEFAULT_EVENT_HANDLER;
		}

		@Override
		public String getHref() {
			try {
				if (!TermModelPermission.contains(
						_permissionChecker, _term, ActionKeys.UPDATE)) {

					return null;
				}

				return _termURL;
			}
			catch (PortalException pe) {
				pe.printStackTrace();
			}
			
			return null;
		}

		@Override
		public String getIcon() {
			return "term";
		}

		@Override
		public String getImageSrc() {
			return "imageSrc";
		}

		@Override
		public String getSubtitle() {
			return _term.getDefinition(_locale);
		}

		@Override
		public String getTitle() {
			return HtmlUtil.escape( _term.getDisplayName(_renderRequest.getLocale()) );
		}

		private final Term _term;
		private final String _termURL;
		private final PermissionChecker _permissionChecker;
		private final RenderRequest  _renderRequest;
		private final RenderResponse _renderResponse;
		private final TrashHelper _trashHelper;
		private final Locale _locale;
}
