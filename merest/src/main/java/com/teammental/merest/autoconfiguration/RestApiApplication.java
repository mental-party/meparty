package com.teammental.merest.autoconfiguration;

import javax.validation.constraints.NotBlank;

public class RestApiApplication {
  /**
   * Name of the rest api application.
   * Should match with the string used in
   * {@link com.teammental.mecore.stereotype.controller.RestApi}
   * annotation.
   */
  @NotBlank
  private String name;

  /**
   * Url of the rest api application.
   */
  @NotBlank
  private String url;
  private Boolean useMockImpl;
  private RestApiBasicAuthProperties basicAuth;

  public RestApiApplication() {
  }

  public RestApiApplication(@NotBlank String name,
                            @NotBlank String url) {
    this.name = name;
    this.url = url;
  }

  /**
   * Constructor.
   *
   * @param name        restApiApplicationRegistry
   * @param url         url
   * @param useMockImpl useMockImpl
   */
  public RestApiApplication(@NotBlank String name,
                            @NotBlank String url,
                            Boolean useMockImpl) {
    this.name = name;
    this.url = url;
    this.useMockImpl = useMockImpl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean getUseMockImpl() {
    return useMockImpl;
  }

  public void setUseMockImpl(boolean useMockImpl) {
    this.useMockImpl = useMockImpl;
  }

  public RestApiBasicAuthProperties getBasicAuth() {
    return basicAuth;
  }

  public void setBasicAuth(RestApiBasicAuthProperties basicAuth) {
    this.basicAuth = basicAuth;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj != null
        && obj instanceof RestApiApplication) {
      return ((RestApiApplication) obj).getName()
          .equalsIgnoreCase(this.name);
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    if (this.getName() != null) {

      return this.getName().hashCode();
    }
    return super.hashCode();
  }
}
