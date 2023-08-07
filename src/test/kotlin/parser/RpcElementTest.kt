package parser

import com.google.protobuf.DescriptorProtos
import com.squareup.wire.schema.Location
import com.squareup.wire.schema.internal.parser.EnumConstantElement
import com.squareup.wire.schema.internal.parser.EnumElement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RpcElementTest {
    @Test
    fun returnsDescriptor() {
        val name = "test"
        val value = DescriptorProtos.EnumValueDescriptorProto.newBuilder().let { builder ->
            builder.setName(name)
            builder.setNumber(0)
            builder.build()
        }
        val descriptor = DescriptorProtos.EnumDescriptorProto.newBuilder().let { builder ->
            builder.setName(name)
            builder.addValue(value)
            builder.build()
        }
        val enumElement = EnumElement(
            Location.get(""),
            name,
            constants = listOf(
                EnumConstantElement(
                    Location.get(""),
                    value.name,
                    value.number
                )
            )
        )
        assertEquals(descriptor, enumElement.enumDescriptorProto())
    }
}