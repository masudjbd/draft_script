package com.atlassian.jwt.httpclient;

import com.atlassian.jwt.CanonicalHttpRequest;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public class CanonicalHttpUriRequest implements CanonicalHttpRequest
{
    private final String method;
    private final String relativePath;
    private final Map<String, String[]> parameterMap;

    public CanonicalHttpUriRequest(final String method, final String path, final String contextPath)
    {
        this(method, path, contextPath, Collections.<String, String[]>emptyMap());
    }

    public CanonicalHttpUriRequest(final String method, final String path, final String contextPath, final Map<String, String[]> parameterMap)
    {
        this.method = checkMethod(method);
        String contextPathToRemove = null == contextPath || "/".equals(contextPath) ? "" : contextPath;
        this.relativePath = StringUtils.defaultIfBlank(StringUtils.removeEnd(StringUtils.removeStart(path, contextPathToRemove), "/"), "/");
        this.parameterMap = parameterMap;
    }

    @Nonnull
    @Override
    public String getMethod()
    {
        return method;
    }

    @Override
    public String getRelativePath()
    {
        return relativePath;
    }

    @Nonnull
    @Override
    public Map<String, String[]> getParameterMap()
    {
        return parameterMap;
    }

    private static String checkMethod(String method)
    {
        if (null == method)
        {
            throw new IllegalArgumentException("Method cannot be null!");
        }

        if ("".equals(method))
        {
            throw new IllegalArgumentException("Method cannot be empty-string!");
        }

        return method.toUpperCase();
    }
}
