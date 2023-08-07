package parser

import com.google.protobuf.DescriptorProtos
import com.google.protobuf.kotlin.toByteStringUtf8
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.internal.parser.EnumElement
import com.squareup.wire.schema.internal.parser.FieldElement
import com.squareup.wire.schema.internal.parser.MessageElement
import com.squareup.wire.schema.internal.parser.ProtoFileElement

/**
 * @param element ProtoFileElement
 * @return DescriptorProtos.FieldDescriptorProto
 * @exception Exception("Unknown field type")
 */

fun FieldElement.fieldDescriptorProto(element: ProtoFileElement): DescriptorProtos.FieldDescriptorProto =
    DescriptorProtos.FieldDescriptorProto.newBuilder().let { builder ->
        label?.let { label ->
            builder.setLabel(DescriptorProtos.FieldDescriptorProto.Label.valueOf("LABEL_${label.name.uppercase()}"))
        }
        val type = when (val protoType = ProtoType.get(type)) {
            ProtoType.BOOL -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_BOOL
            ProtoType.BYTES -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_BYTES
            ProtoType.DOUBLE -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_DOUBLE
            ProtoType.FLOAT -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_FLOAT
            ProtoType.FIXED32 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED32
            ProtoType.FIXED64 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED64
            ProtoType.INT32 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32
            ProtoType.INT64 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT64
            ProtoType.SFIXED32 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_SFIXED32
            ProtoType.SFIXED64 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_SFIXED64
            ProtoType.SINT32 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_SINT32
            ProtoType.SINT64 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_SINT64
            ProtoType.STRING -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING
            ProtoType.UINT32 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_UINT32
            ProtoType.UINT64 -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_UINT64
            else -> {
                when (element.types.firstOrNull { it.name == protoType.simpleName }) {
                    is EnumElement -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_ENUM
                    is MessageElement -> DescriptorProtos.FieldDescriptorProto.Type.TYPE_MESSAGE
                    else -> throw Exception("Unknown field type")
                }.apply {
                    val typeName = arrayOf(
                        element.packageName,
                        type.replaceFirstChar { it.uppercase() }
                    ).joinToString(".")
                    builder.setTypeName(typeName)
                }
            }
        }
        builder.setType(type)
        builder.setName(name)
        builder.setNameBytes(name.toByteStringUtf8())
        defaultValue?.let { defaultValue ->
            builder.setDefaultValue(defaultValue)
            builder.setDefaultValueBytes(defaultValue.toByteStringUtf8())
        }
        jsonName?.let { jsonName ->
            builder.setJsonName(jsonName)
            builder.setJsonNameBytes(jsonName.toByteStringUtf8())
        }
        builder.setNumber(tag)
        builder.build()
    }