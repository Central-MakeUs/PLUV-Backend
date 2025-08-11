package play.pluv.login.exception

import play.pluv.base.BaseException

class LoginException(exceptionType: LoginExceptionType) : BaseException(exceptionType)
