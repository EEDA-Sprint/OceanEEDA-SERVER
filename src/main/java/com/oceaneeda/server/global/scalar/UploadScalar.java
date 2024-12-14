//package com.oceaneeda.server.global.scalar;
//
//import graphql.schema.Coercing;
//import graphql.schema.GraphQLScalarType;
//import org.springframework.web.multipart.MultipartFile;
//
//public class UploadScalar {
//
//    public static final GraphQLScalarType UPLOAD = GraphQLScalarType.newScalar()
//            .name("Upload")
//            .description("A scalar to handle file uploads")
//            .coercing(new Coercing<MultipartFile, MultipartFile>() {
//
//                @Override
//                public MultipartFile serialize(Object dataFetcherResult) {
//                    if (dataFetcherResult instanceof MultipartFile) {
//                        return (MultipartFile) dataFetcherResult;
//                    }
//                    throw new IllegalArgumentException("Expected a MultipartFile object.");
//                }
//
//                @Override
//                public MultipartFile parseValue(Object input) {
//                    if (input instanceof MultipartFile) {
//                        return (MultipartFile) input;
//                    }
//                    throw new IllegalArgumentException("Invalid file upload input. Expected a MultipartFile.");
//                }
//
//                @Override
//                public MultipartFile parseLiteral(Object input) {
//                    throw new IllegalArgumentException("Literal uploads are not supported.");
//                }
//            })
//            .build();
//}