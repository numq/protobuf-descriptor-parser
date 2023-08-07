package parser

import com.google.protobuf.DescriptorProtos
import com.google.protobuf.kotlin.toByteStringUtf8
import com.squareup.wire.schema.internal.parser.EnumConstantElement

/**
 * @return DescriptorProtos.EnumValueDescriptorProto
 */

fun EnumConstantElement.enumValueDescriptorProto(): DescriptorProtos.EnumValueDescriptorProto =
    DescriptorProtos.EnumValueDescriptorProto.newBuilder().let { builder ->
        builder.setName(name)
        builder.setNameBytes(name.toByteStringUtf8())
        builder.setNumber(tag)
        builder.build()
    }

