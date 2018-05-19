package com.helloJob.commons.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CsrfTokenRepository {
	/**
	 * Generates a {@link CsrfTokenBean}
	 *
	 * @param request the {@link HttpServletRequest} to use
	 * @return the {@link CsrfTokenBean} that was generated. Cannot be null.
	 */
	CsrfTokenBean generateToken(HttpServletRequest request);

	/**
	 * Saves the {@link CsrfTokenBean} using the {@link HttpServletRequest} and
	 * {@link HttpServletResponse}. If the {@link CsrfTokenBean} is null, it is the same as
	 * deleting it.
	 *
	 * @param token the {@link CsrfTokenBean} to save or null to delete
	 * @param request the {@link HttpServletRequest} to use
	 * @param response the {@link HttpServletResponse} to use
	 */
	void saveToken(CsrfTokenBean token, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * Loads the expected {@link CsrfTokenBean} from the {@link HttpServletRequest}
	 *
	 * @param request the {@link HttpServletRequest} to use
	 * @return the {@link CsrfTokenBean} or null if none exists
	 */
	CsrfTokenBean loadToken(HttpServletRequest request);
}
