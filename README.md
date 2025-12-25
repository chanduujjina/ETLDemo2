## Below is the sql script

```sql
 create table cc_student_info (std_id int,std_name varchar(255),gender varchar(255),phoneNumber varchar(255),skillset varchar(255),total_experince int);
```

## Create excel any of your drives

```
https://github.com/chanduujjina/ETLDemo2/blob/develop/CC_Student_info.xlsx
```


## Sample Payload to test Stream job

```json
{
    "payLoad": [
        {
            "id": 1,
            "name": "Ravi Kumar",
            "gender": "M",
            "barnch": "CSE",
            "eventTime": "2025-01-20T10:15:30Z"
        },
        {
            "id": 2,
            "name": "Rani",
            "gender": "F",
            "barnch": "ECE",
            "eventTime": "2025-01-20T10:15:40Z"
        },
        {
            "id": 3,
            "name": "Raju",
            "gender": "M",
            "barnch": "MECH",
            "eventTime": "2025-01-20T10:15:55Z"
        }
    ]
}

```

