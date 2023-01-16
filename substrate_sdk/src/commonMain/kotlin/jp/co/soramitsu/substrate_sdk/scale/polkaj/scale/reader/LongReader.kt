package jp.co.soramitsu.substrate_sdk.scale.polkaj.scale.reader

import com.ditchoom.buffer.ByteOrder
import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import jp.co.soramitsu.substrate_sdk.scale.ScaleCodecReader
import jp.co.soramitsu.substrate_sdk.scale.ScaleReader

class LongReader : ScaleReader<Long> {

    override fun read(reader: ScaleCodecReader): Long {
        val buf = PlatformBuffer.allocate(8, byteOrder = ByteOrder.LITTLE_ENDIAN)
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        buf.write(reader.readByte())
        buf.position(0)
        return buf.readLong()
    }
}