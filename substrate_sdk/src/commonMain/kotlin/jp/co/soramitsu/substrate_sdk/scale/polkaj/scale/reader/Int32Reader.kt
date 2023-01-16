package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import com.ditchoom.buffer.ByteOrder
import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

/**
 * Read Java Integer encoded as 4 byte SCALE value. Please note that since Java Integer is signed type, it may
 * read negative values for some of the byte representations (i.e. when highest bit is set to 1). If you expect
 * to read positive numbers for all of the possible range, you should use Uint32Reader, which returns Long values.
 *
 * @see UInt32Reader
 */
class Int32Reader : ScaleReader<Int> {

    override fun read(reader: ScaleCodecReader): Int {
        val buf = PlatformBuffer.allocate(4, byteOrder = ByteOrder.LITTLE_ENDIAN)
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        return 0
        //return buf.flip().getInt(); // TODO: Return it!
    }
}