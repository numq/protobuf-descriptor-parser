package parser

import com.google.protobuf.DescriptorProtos
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.Location
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.internal.parser.FieldElement
import com.squareup.wire.schema.internal.parser.MessageElement
import com.squareup.wire.schema.internal.parser.ProtoFileElement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MessageElementTest {
    @Test
    fun returnsDescriptor() {
        val protoFileElement = ProtoFileElement(
            Location.get("")
        )
        val name = "name"
        val number = 0
        val field = DescriptorProtos.FieldDescriptorProto.newBuilder().let { builder ->
            builder.setLabel(DescriptorProtos.FieldDescriptorProto.Label.LABEL_REPEATED)
            builder.setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32)
            builder.setName(name)
            builder.setNumber(number)
            builder.build()
        }
        val descriptor = DescriptorProtos.DescriptorProto.newBuilder().let { builder ->
            builder.setName(name)
            builder.addField(field)
            builder.build()
        }
        val messageElement = MessageElement(
            Location.get(""),
            name,
            fields = listOf(
                FieldElement(
                    Location.get(""),
                    label = Field.Label.REPEATED,
                    type = ProtoType.INT32.simpleName,
                    name = name,
                    tag = number
                )
            )
        )
        assertEquals(descriptor, messageElement.descriptorProto(protoFileElement))
    }
}