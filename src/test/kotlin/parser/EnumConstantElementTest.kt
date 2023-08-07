package parser

import com.google.protobuf.DescriptorProtos
import com.squareup.wire.schema.Location
import com.squareup.wire.schema.internal.parser.EnumConstantElement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EnumConstantElementTest {
    @Test
    fun returnsDescriptor() {
        val name = "test"
        val number = 0
        val descriptor = DescriptorProtos.EnumValueDescriptorProto.newBuilder().let { builder ->
            builder.setName(name)
            builder.setNumber(number)
            builder.build()
        }
        val enumConstantElement = EnumConstantElement(
            Location.get(""),
            name,
            number
        )
        assertEquals(descriptor, enumConstantElement.enumValueDescriptorProto())
    }
}