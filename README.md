# assn3-javafx
JavaFX starter code

## Developers

Kaitlin Erb,
Reed Orpen

## Run Repo

```bash
// Clone the repo
git clone https://github.com/SENG330/assn3-team-orpenerb.git

// In root of project
gradle bootRun
```

## Contribute to the Repo

#### Creating a Branch and Making Changes

```bash
// Creating a branch
git checkout -b branch-name

// Make changes and see what files differ from master
git status

// Add modified files one at a time
git add {$specific file names}
// OR to add all files
git add .
```

Once small changes have been made, commit and push them.
```bash
git commit -m "Description of Changes"
git push origin branch-name
```

When all changes are made, create a PR and wait for the review/approval process.

#### Merging Code

```bash
# update your origin/* pointers
git fetch
 
# checkout the branch you’re merging in (assumes branch-name points to origin/branch-name)
git checkout branch-name
 
# rebase the whole branch onto master:
git rebase origin/master

# update origin/branch name to the new rebased head
git push --force-with-lease origin branch-name
 
# point your master branch to latest origin/master
git checkout master && git fetch && git reset --hard origin/master
 
# merge in the branch and force a ‘merge commit’
git merge --no-ff branch-name -m "Merge 'your-branch-name'"
 
# push your changes; this will automagically resolve the Github PR
git push origin master
 
# if no issue with merge, you can now delete the branch locally
git branch -d branch-name
 
# put this deleted branch back into origin
git push origin :branch-name
```

See https://github.com/SENG330/course/blob/master/assignment3.md
