package parser

import com.google.protobuf.DescriptorProtos
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.Location
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.internal.parser.FieldElement
import com.squareup.wire.schema.internal.parser.ProtoFileElement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FieldElementTest {
    @Test
    fun returnsDescriptor() {
        val name = "name"
        val defaultValue = "default value"
        val jsonName = "json name"
        val number = 0
        val descriptor = DescriptorProtos.FieldDescriptorProto.newBuilder().let { builder ->
            builder.setLabel(DescriptorProtos.FieldDescriptorProto.Label.LABEL_REPEATED)
            builder.setType(DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32)
            builder.setName(name)
            builder.setDefaultValue(defaultValue)
            builder.setJsonName(jsonName)
            builder.setNumber(number)
            builder.build()
        }
        val protoFileElement = ProtoFileElement(Location.get(""))
        val fieldElement = FieldElement(
            Location.Companion.get(""),
            Field.Label.REPEATED,
            ProtoType.INT32.simpleName,
            name,
            defaultValue,
            jsonName,
            number
        )
        assertEquals(descriptor, fieldElement.fieldDescriptorProto(protoFileElement))
    }
}