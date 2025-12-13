# Internal Working of beam 

### In a DoFn class, we don’t override any interface method,yet when we annotate a method with @ProcessElement,Beam magically treats it as the main processing method ?

 - 👉 Apache Beam uses reflection + annotations to discover lifecycle methods at runtime.
 - @ProcessElement is not magic — it’s metadata.

 ### ✅ Annotation-Based Approach (Beam)
 - Beam allows multiple special methods:
 
 | Annotation        | Purpose                |
| ----------------- | ---------------------- |
| `@Setup`          | Once per DoFn instance |
| `@StartBundle`    | Per bundle             |
| `@ProcessElement` | Per element            |
| `@FinishBundle`   | End of bundle          |
| `@Teardown`       | Cleanup                |

### 🔥 How Beam Executes @ProcessElement
- Beam creates a DoFnInvoker

- It scans the DoFn class

- Finds the method annotated with @ProcessElement

- Validates method signature

- Caches the method reference

- Calls it once per element

### 🧠 How Beam Knows Method Parameters
### Beam inspects parameters using annotations:

| Parameter            | Meaning         |
| -------------------- | --------------- |
| `@Element T`         | Current element |
| `OutputReceiver<T>`  | Output          |
| `ProcessContext`     | Full context    |
| `@Timestamp Instant` | Event time      |
| `@PaneInfo PaneInfo` | Window info     |
| `@SideInput`         | Side input      |

### Beam injects values automatically.
#### ✨ Example: Parameter Injection

```java
@ProcessElement
public void process(
    @Element String line,
    OutputReceiver<String> out,
    ProcessContext ctx,
    @Timestamp Instant ts) {

    out.output(line + " @ " + ts);
}
```
### Beam decides :
- What to pass

- When to pass

- From where to pass

### Windowing & Watermark – Sequence Diagram
```mermaid
sequenceDiagram
    participant Source as Event Source
    participant Beam as Apache Beam
    participant WM as Watermark
    participant Win as Fixed Window (0–60s)
    participant Agg as Aggregation
    participant Sink as Output Sink

    Source->>Beam: Event A (eventTime = 10s)
    Beam->>WM: Update watermark → 10s
    Beam->>Win: Assign Event A to Window [0–60)

    Source->>Beam: Event B (eventTime = 40s)
    Beam->>WM: Update watermark → 40s
    Beam->>Win: Assign Event B to Window [0–60)

    Source->>Beam: Event C (eventTime = 70s)
    Beam->>WM: Update watermark → 70s

    WM->>Win: Watermark passes window end (60s)
    Win->>Agg: Trigger computation
    Agg->>Sink: Emit aggregated result

    Source->>Beam: Late Event D (eventTime = 30s)
    Beam->>WM: Watermark already > 60s
    Beam-->>Win: Late data (dropped or side output)
```


## 1️⃣ Window + Trigger Diagram

```mermaid
sequenceDiagram
    participant Src as Event Source
    participant Beam as Apache Beam
    participant Win as Fixed Window (0–60s)
    participant Trg as Event-Time Trigger
    participant Sink as Output

    Src->>Beam: Event A (t=10s)
    Beam->>Win: Assign to Window [0–60)

    Src->>Beam: Event B (t=20s)
    Beam->>Win: Assign to Window [0–60)

    Trg-->>Win: Trigger condition not met

    Src->>Beam: Event C (t=40s)
    Beam->>Win: Assign to Window [0–60)

    Trg-->>Win: Waiting for watermark

    Note over Beam: Watermark advances to 60s

    Trg->>Win: Event-time trigger fires
    Win->>Sink: Emit aggregated result
```

# ## Key idea

- Window decides where events go

- Trigger decides when results are emitted

## 2️⃣ Late Data with Allowed Lateness Diagram

```mermaid
sequenceDiagram
    participant Src as Event Source
    participant Beam as Apache Beam
    participant WM as Watermark
    participant Win as Fixed Window (0–60s)
    participant Trg as Trigger
    participant Sink as Output
    participant Late as Late Data Output

    Src->>Beam: Event A (t=10s)
    Beam->>WM: Watermark → 10s
    Beam->>Win: Add to Window [0–60)

    Src->>Beam: Event B (t=50s)
    Beam->>WM: Watermark → 50s
    Beam->>Win: Add to Window [0–60)

    Src->>Beam: Event C (t=70s)
    Beam->>WM: Watermark → 70s

    WM->>Trg: Watermark passes window end
    Trg->>Win: Fire (ON-TIME)
    Win->>Sink: Emit ON-TIME result

    Note over Win: Allowed lateness = 30s

    Src->>Beam: Late Event D (t=40s)
    Beam->>WM: Watermark = 70s
    Beam->>Win: Still within allowed lateness

    Trg->>Win: Fire again (LATE)
    Win->>Sink: Emit UPDATED result

    Src->>Beam: Too Late Event E (t=20s)
    Beam-->>Late: Drop or send to late-data output
```
### Mental Model (Easy to Remember)
- Window → groups data
- Watermark → time progress
- Trigger → when to emit
- Allowed lateness → how long to wait for late events
