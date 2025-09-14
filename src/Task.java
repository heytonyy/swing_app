/**
 * Task class to represent individual to-do items
 * Students will use this as an example model class
 */
class Task {
    private String description;
    private boolean complete;
    
    /**
     * Constructor for a new task
     * @param description The task description
     */
    public Task(String description) {
        this.description = description;
        this.complete = false;
    }
    
    /**
     * Gets the task description
     * @return The task description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Checks if the task is complete
     * @return true if complete, false otherwise
     */
    public boolean isComplete() {
        return complete;
    }
    
    /**
     * Toggles the completion status of the task
     */
    public void toggleComplete() {
        complete = !complete;
    }
    
    /**
     * Sets the task description
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Sets the completion status
     * @param complete The completion status
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    
    @Override
    public String toString() {
        return (complete ? "[DONE] " : "[TODO] ") + description;
    }
}