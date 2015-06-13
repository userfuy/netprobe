/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_jnetpcap_nio_JMemory */

#ifndef _Included_org_jnetpcap_nio_JMemory
#define _Included_org_jnetpcap_nio_JMemory
#ifdef __cplusplus
extern "C" {
#endif
#undef org_jnetpcap_nio_JMemory_MAX_DIRECT_MEMORY_DEFAULT
#define org_jnetpcap_nio_JMemory_MAX_DIRECT_MEMORY_DEFAULT 67108864i64
/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    allocate0
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_nio_JMemory_allocate0
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    availableDirectMemory
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_nio_JMemory_availableDirectMemory
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    initIDs
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_jnetpcap_nio_JMemory_initIDs
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    reservedDirectMemory
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_nio_JMemory_reservedDirectMemory
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    setMaxDirectMemorySize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_jnetpcap_nio_JMemory_setMaxDirectMemorySize
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    setSoftDirectMemorySize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_jnetpcap_nio_JMemory_setSoftDirectMemorySize
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    totalAllocateCalls
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_nio_JMemory_totalAllocateCalls
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    totalAllocated
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_nio_JMemory_totalAllocated
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    totalAllocatedSegments0To255Bytes
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_nio_JMemory_totalAllocatedSegments0To255Bytes
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    totalAllocatedSegments256OrAbove
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_nio_JMemory_totalAllocatedSegments256OrAbove
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    totalDeAllocateCalls
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_nio_JMemory_totalDeAllocateCalls
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    totalDeAllocated
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_jnetpcap_nio_JMemory_totalDeAllocated
  (JNIEnv *, jclass);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    transferTo0
 * Signature: (J[BIII)I
 */
JNIEXPORT jint JNICALL Java_org_jnetpcap_nio_JMemory_transferTo0
  (JNIEnv *, jclass, jlong, jbyteArray, jint, jint, jint);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    peer
 * Signature: (Ljava/nio/ByteBuffer;)I
 */
JNIEXPORT jint JNICALL Java_org_jnetpcap_nio_JMemory_peer
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    transferFrom
 * Signature: ([BIII)I
 */
JNIEXPORT jint JNICALL Java_org_jnetpcap_nio_JMemory_transferFrom
  (JNIEnv *, jobject, jbyteArray, jint, jint, jint);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    transferFromDirect
 * Signature: (Ljava/nio/ByteBuffer;I)I
 */
JNIEXPORT jint JNICALL Java_org_jnetpcap_nio_JMemory_transferFromDirect
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    transferTo
 * Signature: (Lorg/jnetpcap/nio/JMemory;III)I
 */
JNIEXPORT jint JNICALL Java_org_jnetpcap_nio_JMemory_transferTo
  (JNIEnv *, jobject, jobject, jint, jint, jint);

/*
 * Class:     org_jnetpcap_nio_JMemory
 * Method:    transferToDirect
 * Signature: (Ljava/nio/ByteBuffer;II)I
 */
JNIEXPORT jint JNICALL Java_org_jnetpcap_nio_JMemory_transferToDirect
  (JNIEnv *, jobject, jobject, jint, jint);

#ifdef __cplusplus
}
#endif
#endif