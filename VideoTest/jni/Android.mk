LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := my-ndk
LOCAL_SRC_FILES := my-ndk.cpp

include $(BUILD_SHARED_LIBRARY)
