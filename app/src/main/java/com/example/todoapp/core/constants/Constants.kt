package com.example.todoapp.core.constants

@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

// The name of the todo manipulation work
const val TODO_MANIPULATION_WORK_NAME = "TODO_MANIPULATION_WORK"

const val KEY_TODO_NAME = "KEY_TODO_NAME"
const val KEY_TODO_ID = "KEY_TODO_ID"

//Tag
const val TAG_OUTPUT = "OUTPUT"
const val TAG_REMOVE_TODO_WORKER = "REMOVE_TODO_WORKER"
const val TAG_WORKER_UTILS = "WORKER_UTILS"
const val TAG_CLEAN_UP_WORKER = "CLEAN_UP_WORKER"
const val TAG_TODO_WORKER = "TODO_WORKER"

const val FILE_NAME_TODO = "file_name_todo"
const val DELAY_TIME_MILLIS: Long = 3000