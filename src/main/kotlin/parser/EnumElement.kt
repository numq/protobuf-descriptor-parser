package parser

import com.google.protobuf.DescriptorProtos
import com.google.protobuf.kotlin.toByteStringUtf8
import com.squareup.wire.schema.internal.parser.EnumConstantElement
import com.squareup.wire.schema.internal.parser.EnumElement

/**
 * @return DescriptorProtos.EnumDescriptorProto
 */

fun EnumElement.enumDescriptorProto(): DescriptorProtos.EnumDescriptorProto =
    DescriptorProtos.EnumDescriptorProto.newBuilder().let { builder ->
        builder.setName(name)
        builder.setNameBytes(name.toByteStringUtf8())
        builder.addAllValue(constants.map(EnumConstantElement::enumValueDescriptorProto))
        reserveds.forEach { reservedElement ->
            reservedElement.values.forEach { value ->
                when (value) {
                    is String -> {
                        builder.addReservedName(value)
                        builder.addReservedNameBytes(value.toByteStringUtf8())
                    }

                    is Int -> {
                        val range =
                            DescriptorProtos.EnumDescriptorProto.EnumReservedRange.newBuilder().let { rangeBuilder ->
                                rangeBuilder.setStart(value)
                                rangeBuilder.setEnd(value)
                                rangeBuilder.build()
                            }
                        builder.addReservedRange(range)
                    }

                    is IntRange -> {
                        val range =
                            DescriptorProtos.EnumDescriptorProto.EnumReservedRange.newBuilder().let { rangeBuilder ->
                                rangeBuilder.setStart(value.first)
                                rangeBuilder.setEnd(value.last)
                                rangeBuilder.build()
                            }
                        builder.addReservedRange(range)
                    }
                }
            }
        }
        builder.build()
    }