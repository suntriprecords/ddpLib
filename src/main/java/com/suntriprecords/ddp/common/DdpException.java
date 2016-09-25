package com.suntriprecords.ddp.common;

public class DdpException extends Exception {
  private static final long serialVersionUID = 1L;

  public DdpException() {
    //nothing
  }

  public DdpException(Throwable cause) {
    super(cause);
  }

  public DdpException(String message) {
    super(message);
  }

  public DdpException(String message, Throwable cause) {
    super(message, cause);
  }
}
