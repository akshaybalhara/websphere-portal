package com.ibm.generichelloworldportletproject;

import java.io.*;
import javax.portlet.*;

/**
 * A sample portlet based on GenericPortlet
 */
public class GenericHelloWorldPortletProjectPortlet extends GenericPortlet {

	public static final String JSP_FOLDER    = "/_GenericHelloWorldPortletProject/jsp/";    // JSP folder name

	public static final String VIEW_JSP      = "GenericHelloWorldPortletProjectPortletView";         // JSP file name to be rendered on the view mode
	public static final String SESSION_BEAN  = "GenericHelloWorldPortletProjectPortletSessionBean";  // Bean name for the portlet session
	public static final String FORM_SUBMIT   = "GenericHelloWorldPortletProjectPortletFormSubmit";   // Action name for submit form
	public static final String FORM_TEXT     = "GenericHelloWorldPortletProjectPortletFormText";     // Parameter name for the text input



	 
	/**
	 * @see javax.portlet.Portlet#init()
	 */
	public void init() throws PortletException{
		super.init();
	}

	/**
	 * Serve up the <code>view</code> mode.
	 * 
	 * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		// Set the MIME type for the render response
		response.setContentType(request.getResponseContentType());

		// Check if portlet session exists
		GenericHelloWorldPortletProjectPortletSessionBean sessionBean = getSessionBean(request);
		if( sessionBean==null ) {
			response.getWriter().println("<b>NO PORTLET SESSION YET</b>");
			return;
		}

		// Invoke the JSP to render
		PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(getJspFilePath(request, VIEW_JSP));
		rd.include(request,response);
	}

	/**
	 * Process an action request.
	 * 
	 * @see javax.portlet.Portlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
	 */
	public void processAction(ActionRequest request, ActionResponse response) throws PortletException, java.io.IOException {
		if( request.getParameter(FORM_SUBMIT) != null ) {
			// Set form text in the session bean
			GenericHelloWorldPortletProjectPortletSessionBean sessionBean = getSessionBean(request);
			if( sessionBean != null )
				sessionBean.setFormText(request.getParameter(FORM_TEXT));
		}
	}

	/**
	 * Get SessionBean.
	 * 
	 * @param request PortletRequest
	 * @return GenericHelloWorldPortletProjectPortletSessionBean
	 */
	private static GenericHelloWorldPortletProjectPortletSessionBean getSessionBean(PortletRequest request) {
		PortletSession session = request.getPortletSession();
		if( session == null )
			return null;
		GenericHelloWorldPortletProjectPortletSessionBean sessionBean = (GenericHelloWorldPortletProjectPortletSessionBean)session.getAttribute(SESSION_BEAN);
		if( sessionBean == null ) {
			sessionBean = new GenericHelloWorldPortletProjectPortletSessionBean();
			session.setAttribute(SESSION_BEAN,sessionBean);
		}
		return sessionBean;
	}

	/**
	 * Returns JSP file path.
	 * 
	 * @param request Render request
	 * @param jspFile JSP file name
	 * @return JSP file path
	 */
	private static String getJspFilePath(RenderRequest request, String jspFile) {
		String markup = request.getProperty("wps.markup");
		if( markup == null )
			markup = getMarkup(request.getResponseContentType());
		return JSP_FOLDER + markup + "/" + jspFile + "." + getJspExtension(markup);
	}

	/**
	 * Convert MIME type to markup name.
	 * 
	 * @param contentType MIME type
	 * @return Markup name
	 */
	private static String getMarkup(String contentType) {
		if( "text/vnd.wap.wml".equals(contentType) )
			return "wml";
        else
            return "html";
	}

	/**
	 * Returns the file extension for the JSP file
	 * 
	 * @param markupName Markup name
	 * @return JSP extension
	 */
	private static String getJspExtension(String markupName) {
		return "jsp";
	}

}
