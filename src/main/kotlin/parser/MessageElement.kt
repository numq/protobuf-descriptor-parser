package parser

import com.google.protobuf.DescriptorProtos
import com.google.protobuf.kotlin.toByteStringUtf8
import com.squareup.wire.schema.internal.parser.EnumElement
import com.squareup.wire.schema.internal.parser.MessageElement
import com.squareup.wire.schema.internal.parser.ProtoFileElement

/**
 * @param element ProtoFileElement
 * @return DescriptorProtos.DescriptorProto
 */

fun MessageElement.descriptorProto(element: ProtoFileElement): DescriptorProtos.DescriptorProto =
    DescriptorProtos.DescriptorProto.newBuilder().let { builder ->
        builder.setName(name)
        builder.setNameBytes(name.toByteStringUtf8())
        builder.addAllEnumType(
            nestedTypes.filterIsInstance<EnumElement>().map(EnumElement::enumDescriptorProto)
        )
        builder.addAllNestedType(
            nestedTypes.filterIsInstance<MessageElement>().map { it.descriptorProto(element) }
        )
        reserveds.forEach { reservedElement ->
            reservedElement.values.forEach { value ->
                when (value) {
                    is String -> {
                        builder.addReservedName(value)
                        builder.addReservedNameBytes(value.toByteStringUtf8())
                    }

                    is Int -> {
                        val range =
                            DescriptorProtos.DescriptorProto.ReservedRange.newBuilder().let { rangeBuilder ->
                                rangeBuilder.setStart(value)
                                rangeBuilder.setEnd(value)
                                rangeBuilder.build()
                            }
                        builder.addReservedRange(range)
                    }

                    is IntRange -> {
                        val range =
                            DescriptorProtos.DescriptorProto.ReservedRange.newBuilder().let { rangeBuilder ->
                                rangeBuilder.setStart(value.first)
                                rangeBuilder.setEnd(value.last)
                                rangeBuilder.build()
                            }
                        builder.addReservedRange(range)
                    }
                }
            }
        }
        builder.addAllField(fields.map { it.fieldDescriptorProto(element) })
        extensions.forEach { extensionsElement ->
            extensionsElement.values.forEach { value ->
                when (value) {
                    is Int -> {
                        val range = DescriptorProtos.DescriptorProto.ExtensionRange.newBuilder().let { builder ->
                            builder.setStart(value)
                            builder.setEnd(value)
                            builder.build()
                        }
                        builder.addExtensionRange(range)
                    }

                    is IntRange -> {
                        val range = DescriptorProtos.DescriptorProto.ExtensionRange.newBuilder().let { builder ->
                            builder.setStart(value.first)
                            builder.setEnd(value.last)
                            builder.build()
                        }
                        builder.addExtensionRange(range)
                    }
                }
            }
        }
        builder.build()
    }