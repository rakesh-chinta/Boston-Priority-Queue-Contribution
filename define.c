#define NUM_PRIO_QUEUES (32) 
#define REALTIME_QID    (NUM_PRIO_QUEUES)
static struct list_head prio_queues[NUM_PRIO_QUEUES+1]; 
int topqid = -1;
