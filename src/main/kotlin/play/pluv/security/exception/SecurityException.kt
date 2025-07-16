package play.pluv.security.exception

import play.pluv.base.BaseException

class SecurityException(exceptionType: SecurityExceptionType) : BaseException(exceptionType)
