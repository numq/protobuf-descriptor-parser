package parser

import com.google.protobuf.DescriptorProtos
import com.squareup.wire.schema.Location
import com.squareup.wire.schema.internal.parser.ProtoFileElement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ProtoFileElementTest {
    @Test
    fun returnsDescriptor() {
        val name = "name"
        val descriptor = DescriptorProtos.FileDescriptorProto.newBuilder().let { builder ->
            builder.setName(name)
            builder.build()
        }
        val messageElement = ProtoFileElement(
            Location.get(""),
        )
        assertEquals(descriptor, messageElement.fileDescriptorProto(name))
    }
}