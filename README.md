# IB Computer Science Internal Assessment
**First Assessment 2027**

---

## Student Information

| Field | Details |
|---|---|
| **Candidate Number** | [IB Candidate Number] |
| **Level** | [ ] SL &nbsp;&nbsp; [x] HL |
| **Academic Year** | 2025–2027 |

---

## Project Overview

### Problem Title
> *[A concise title for your computational solution — e.g., "Library Book Management System" or "Pathfinding Visualiser for Maze Solving"]*

### Computational Context
> *[One sentence naming the area of computer science your solution draws on — e.g., "Object-oriented programming using Python with a SQLite database backend."]*

### Problem Description
> *[2–4 sentences describing the real-world problem you are solving, who it affects, and why a computational solution is appropriate. This should reflect your Criterion A problem scenario.]*

---

## Success Criteria

These criteria are defined in full in the Criterion A section of the documentation. They are listed here so commit messages can reference them directly (e.g., `TEST: Verify SC-3 handles empty input`).

| ID | Success Criterion | Status |
|---|---|---|
| SC-1 | [e.g., The system allows a user to add, edit and delete records] | 🔲 Not started |
| SC-2 | [e.g., Search returns results in under 2 seconds for a dataset of 1000 entries] | 🔲 Not started |
| SC-3 | [e.g., Invalid inputs are caught and an appropriate error message is displayed] | 🔲 Not started |
| SC-4 | [e.g., The user interface is navigable without prior instruction] | 🔲 Not started |
| SC-5 | [Add as many as your problem requires] | 🔲 Not started |

*Update status as development progresses:* 🔲 Not started → 🔄 In progress → ✅ Complete

---

## Repository Structure

```
/
├── README.md               ← This file
├── /src                    ← All source code
│   ├── main.[py/js/etc]    ← Entry point
│   └── ...
├── /docs                   ← Planning and design artefacts
│   ├── system_model.png    ← Criterion C system diagram
│   ├── ui_mockups/         ← User interface designs
│   └── algorithms/         ← Flowcharts or pseudocode
└── /tests                  ← Testing evidence
    ├── test_plan.md        ← Testing strategy table (Criterion C)
    └── screenshots/        ← Evidence of tests run (referenced in video)
```

---

## Development Plan Summary

*This summarises the Criterion B plan. Each phase below corresponds to a milestone that commit messages should reference.*

| Phase | Milestone | Target Date | Commits to Reference |
|---|---|---|---|
| 1 | Problem specification &amp; research complete | [Date] | `PLAN:` |
| 2 | System design &amp; algorithms finalised | [Date] | `PLAN:` / `DOCS:` |
| 3 | Core functionality built | [Date] | `BUILD:` |
| 4 | Additional features implemented | [Date] | `BUILD:` |
| 5 | Testing &amp; debugging | [Date] | `TEST:` / `FIX:` |
| 6 | Documentation complete | [Date] | `DOCS:` |
| 7 | Final review &amp; submission | [Date] | `DOCS:` |

---

## Commit Message Convention

All commits in this repository follow this format:

```
[TYPE]: Brief description of what changed — [SC-reference if applicable]
```

| Type | When to use |
|---|---|
| `PLAN` | Design decisions, diagrams, planning documents added or updated |
| `BUILD` | New feature, function, or component of the product added |
| `FIX` | A bug identified during testing has been corrected |
| `TEST` | Testing evidence added, or code updated in response to a test result |
| `DOCS` | Comments added to code, or IA documentation sections updated |
| `REFACTOR` | Code restructured for clarity or efficiency without changing behaviour |

**Examples:**
```
BUILD: Implement binary search for product lookup — SC-2
FIX: Resolve index out of bounds error in sort function
TEST: Edge case — empty input returns null as expected — SC-3
DOCS: Add inline comments to database connection module
```

---

## Academic Integrity Declaration

By committing to this repository, I confirm that:

- All code and documentation is my own work unless explicitly referenced
- Any code adapted from external sources is clearly commented with the source URL and the nature of the modification
- This repository represents the authentic, incremental development of my IA product

*Signed:* [Student Name] &nbsp;&nbsp; *Date of first commit:* [Date]

---

*IB Diploma Programme · Computer Science · Internal Assessment · First Assessment 2027*
