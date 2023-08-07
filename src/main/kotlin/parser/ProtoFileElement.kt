package parser

import com.google.protobuf.DescriptorProtos
import com.google.protobuf.kotlin.toByteStringUtf8
import com.squareup.wire.schema.internal.parser.EnumElement
import com.squareup.wire.schema.internal.parser.MessageElement
import com.squareup.wire.schema.internal.parser.ProtoFileElement

/**
 * @param name String
 * @return DescriptorProtos.FileDescriptorProto
 */

fun ProtoFileElement.fileDescriptorProto(name: String): DescriptorProtos.FileDescriptorProto =
    DescriptorProtos.FileDescriptorProto.newBuilder().also { builder ->
        packageName?.let { packageName ->
            builder.setPackage(packageName)
            builder.setPackageBytes(packageName.toByteStringUtf8())
        }
        builder.setName(name)
        builder.setNameBytes(name.toByteStringUtf8())
        syntax?.name?.let(builder::setSyntax)
        builder.addAllDependency(imports)
        builder.addAllPublicDependency(publicImports.mapNotNull(String::toIntOrNull))
        types.forEach { type ->
            if (type is EnumElement) builder.addEnumType(type.enumDescriptorProto())
            if (type is MessageElement) builder.addMessageType(type.descriptorProto(this))
        }
        services.forEach { service ->
            builder.addService(service.serviceDescriptorProto())
        }
    }.build()