package com.example.todoapp.constants

// Notification Channel constants

// Name of Notification Channel for verbose notifications of background work
@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

// The name of the image manipulation work
const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

// Other keys
const val OUTPUT_PATH = "blur_filter_outputs"
const val KEY_TODO_NAME = "KEY_TODO_NAME"
const val KEY_TODO_ID = "KEY_TODO_ID"
const val TAG_OUTPUT = "OUTPUT"

const val FILE_NAME_TODO = "file_name_todo"

const val DELAY_TIME_MILLIS: Long = 3000