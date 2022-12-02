package jp.co.soramitsu.xnetworking.networkclient

open class SoramitsuNetworkException(val m: String, c: Throwable?) : Throwable(m, c)

class CodeNetworkException(val code: Int, m: String, c: Throwable?) : SoramitsuNetworkException(m, c)

class SerializationNetworkException(m: String, c: Throwable?) : SoramitsuNetworkException(m, c)

class GeneralNetworkException(m: String, c: Throwable?) : SoramitsuNetworkException(m, c)