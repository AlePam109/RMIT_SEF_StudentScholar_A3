const fs = require('fs');

const W = 2600, H = 3300;
const topY = 115, boxH = 82, lifeTop = 197, lifeBottom = 3150;
const xs = [130, 415, 700, 985, 1270, 1555, 1840, 2125, 2410];
const names = [
  ['Teaching Staff', 'actor'],
  ['gradingUI:GradingPage', 'boundary'],
  ['gradingController:GradingController', 'control'],
  ['course:Course', 'entity'],
  ['assessment:Assessment', 'entity'],
  ['submission:Submission', 'entity'],
  ['rubric:Rubric', 'entity'],
  ['similarityService:SimilarityCheckService', 'external'],
  ['gradeRecord:GradeRecord', 'entity'],
];

const esc = s => String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
const out = [];
const add = s => out.push(s);
const rowY = r => 230 + r * 49;

add(`<svg xmlns="http://www.w3.org/2000/svg" width="${W}" height="${H}" viewBox="0 0 ${W} ${H}">`);
add(`<defs>
  <marker id="arrow" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" orient="auto"><path d="M0 0 L10 5 L0 10z" fill="#263238"/></marker>
  <marker id="openArrow" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" orient="auto"><path d="M0 0 L10 5 L0 10" fill="none" stroke="#263238" stroke-width="1.5"/></marker>
  <style>
    .title{font:700 36px Arial,sans-serif;fill:#17212b}.head{fill:#d9ebff;stroke:#2867ad;stroke-width:2.5}.headText{font:600 16px Arial,sans-serif;fill:#101820;text-anchor:middle}.stereo{font:italic 14px Arial,sans-serif;fill:#40576e;text-anchor:middle}
    .life{stroke:#677785;stroke-width:2;stroke-dasharray:9 8}.activation{fill:#fff;stroke:#2867ad;stroke-width:2}.message{stroke:#263238;stroke-width:2.3;fill:none;marker-end:url(#arrow)}.return{stroke:#263238;stroke-width:2;stroke-dasharray:8 6;fill:none;marker-end:url(#openArrow)}
    .msgText{font:15px Arial,sans-serif;fill:#111;text-anchor:middle}.frame{fill:none;stroke:#536473;stroke-width:2}.divider{stroke:#536473;stroke-width:1.8;stroke-dasharray:8 6}.frameLabel{font:700 16px Arial,sans-serif;fill:#111}.guard{font:italic 15px Arial,sans-serif;fill:#111}.legend{font:16px Arial,sans-serif;fill:#23374b}
  </style>
</defs>`);
add(`<rect width="${W}" height="${H}" fill="#fff"/>`);
add(`<text class="title" x="${W/2}" y="52" text-anchor="middle">Sequence Diagram - Grade Submission</text>`);

// Combined-fragment frames are drawn behind lifelines and messages.
function frame(x, y, w, h, label, guard, dividers=[]) {
  add(`<rect class="frame" x="${x}" y="${y}" width="${w}" height="${h}"/>`);
  const tabW = Math.max(70, label.length*10+22);
  add(`<path class="frame" d="M${x} ${y}h${tabW}v28l-15 14h-${tabW-15}z" fill="#f4f7fa"/>`);
  add(`<text class="frameLabel" x="${x+10}" y="${y+21}">${esc(label)}</text>`);
  if (guard) add(`<text class="guard" x="${x+tabW+12}" y="${y+22}">${esc(guard)}</text>`);
  for (const d of dividers) {
    const dy = rowY(d)-24;
    add(`<line class="divider" x1="${x}" y1="${dy}" x2="${x+w}" y2="${dy}"/>`);
  }
}

frame(80, rowY(5)-36, 720, rowY(6)-rowY(5)+64, 'break', '[not authorised]');
frame(610, rowY(14)-36, 1370, rowY(17)-rowY(14)+64, 'par', '[load submission content]', [16]);
frame(80, rowY(20)-36, 2240, rowY(32)-rowY(20)+64, 'opt', '[similarity check requested]');
frame(620, rowY(25)-36, 1690, rowY(32)-rowY(25)+64, 'alt', '[service available]', [30]);
frame(80, rowY(33)-36, 2440, rowY(48)-rowY(33)+64, 'loop', '[for each rubric criterion]');
frame(620, rowY(36)-36, 1880, rowY(46)-rowY(36)+64, 'alt', '[invalid criterion information]', [39,45]);
add(`<text class="guard" x="640" y="${rowY(30)-31}">[service unavailable or content unsupported]</text>`);
add(`<text class="guard" x="640" y="${rowY(39)-31}">[custom mark]</text>`);
add(`<text class="guard" x="640" y="${rowY(45)-31}">[standard rubric mark]</text>`);

// Lifelines.
for (let i=0;i<xs.length;i++) add(`<line class="life" x1="${xs[i]}" y1="${lifeTop}" x2="${xs[i]}" y2="${lifeBottom}"/>`);

// Activation bars.
function activation(i, r1, r2, offset=0) {
  const x=xs[i]-7+offset;
  add(`<rect class="activation" x="${x}" y="${rowY(r1)-18}" width="14" height="${rowY(r2)-rowY(r1)+36}"/>`);
}
activation(1,1,57); activation(2,2,56);
activation(3,3,4); activation(4,9,10); activation(5,14,15); activation(6,16,17);
activation(5,22,23); activation(7,24,30); activation(5,26,27); activation(6,35,45); activation(8,44,55);

function msg(r, from, to, label, ret=false) {
  const y=rowY(r), x1=xs[from], x2=xs[to];
  const klass=ret?'return':'message';
  add(`<line class="${klass}" x1="${x1}" y1="${y}" x2="${x2}" y2="${y}"/>`);
  add(`<rect x="${Math.min(x1,x2)+10}" y="${y-19}" width="${Math.abs(x2-x1)-20}" height="19" fill="#fff" opacity="0.94"/>`);
  add(`<text class="msgText" x="${(x1+x2)/2}" y="${y-5}">${esc(label)}</text>`);
}

msg(1,0,1,'1: openAssignedCourse(courseId)');
msg(2,1,2,'2: openGrading(courseId)');
msg(3,2,3,'3: verifyAssignment(staffId, courseId)');
msg(4,3,2,'4: assignmentStatus',true);
msg(5,2,1,'5a: displayAccessError()');
msg(6,1,0,'5b: showAccessDenied()');
msg(7,0,1,'6: selectAssessment(assessmentId)');
msg(8,1,2,'7: getSubmissions(assessmentId)');
msg(9,2,4,'8: listSubmissions()');
msg(10,4,2,'9: submissions',true);
msg(11,2,1,'10: displaySubmissions(submissions)');
msg(12,0,1,'11: selectSubmission(submissionId)');
msg(13,1,2,'12: loadGradingWorkspace(submissionId)');
msg(14,2,5,'13a: getContent()');
msg(15,5,2,'13b: submissionContent',true);
msg(16,2,6,'13c: getCriteria()');
msg(17,6,2,'13d: rubricCriteria',true);
msg(18,2,1,'14: displayGradingWorkspace(content, criteria)');
msg(19,1,0,'15: showSubmissionAndRubric()');

msg(20,0,1,'16: requestSimilarityCheck()');
msg(21,1,2,'17: checkSimilarity(submissionId)');
msg(22,2,5,'18: getSupportedContent()');
msg(23,5,2,'19: supportedContent',true);
msg(24,2,7,'20: analyse(content)');
msg(25,7,2,'21a: similarityReport',true);
msg(26,2,5,'22a: linkSimilarityReport(report)');
msg(27,5,2,'23a: reportLinked',true);
msg(28,2,1,'24a: displaySimilarityReport(report)');
msg(29,1,0,'25a: showSimilarityReport()');
msg(30,7,2,'21b: serviceError',true);
msg(31,2,1,'22b: displaySimilarityUnavailable()');
msg(32,1,0,'23b: showWarning()');

msg(33,0,1,'26: enterCriterionMark(criterionId, mark, comment)');
msg(34,1,2,'27: submitCriterionMark(criterionId, mark, comment)');
msg(35,2,6,'28: validateMark(criterionId, mark)');
msg(36,6,2,'29a: validationError',true);
msg(37,2,1,'30a: displayValidationError(permittedRange)');
msg(38,1,0,'31a: requestCorrection()');
msg(39,6,2,'29b: customMarkRequiresJustification',true);
msg(40,2,1,'30b: requestJustification()');
msg(41,1,0,'31b: showJustificationPrompt()');
msg(42,0,1,'32b: enterJustification(text)');
msg(43,1,2,'33b: submitJustification(text)');
msg(44,2,8,'34b: recordCriterionMark(mark, comment, justification)');
msg(45,6,2,'29c: validStandardMark',true);
msg(46,2,8,'30c: recordCriterionMark(mark, comment)');
msg(47,8,2,'35: criterionRecorded',true);
msg(48,2,1,'36: criterionAccepted()');

msg(49,0,1,'37: enterOverallFeedback(feedback)');
msg(50,0,1,'38: selectSaveDraft()');
msg(51,1,2,'39: saveDraft(feedback)');
msg(52,2,8,'40: calculateTotal()');
msg(53,8,2,'41: totalMark',true);
msg(54,2,8,'42: saveDraft(totalMark, feedback)');
msg(55,8,2,'43: draftSaved',true);
msg(56,2,1,'44: displayDraftConfirmation(totalMark)');
msg(57,1,0,'45: showDraftSavedConfirmation()');

// Participant heads and actor.
add(`<circle cx="${xs[0]}" cy="105" r="19" fill="none" stroke="#263238" stroke-width="3"/>`);
add(`<line x1="${xs[0]}" y1="124" x2="${xs[0]}" y2="158" stroke="#263238" stroke-width="3"/><line x1="${xs[0]-28}" y1="137" x2="${xs[0]+28}" y2="137" stroke="#263238" stroke-width="3"/><line x1="${xs[0]}" y1="158" x2="${xs[0]-24}" y2="185" stroke="#263238" stroke-width="3"/><line x1="${xs[0]}" y1="158" x2="${xs[0]+24}" y2="185" stroke="#263238" stroke-width="3"/>`);
add(`<text class="headText" x="${xs[0]}" y="212">Teaching Staff</text>`);
for (let i=1;i<xs.length;i++) {
  add(`<rect class="head" x="${xs[i]-105}" y="${topY}" width="210" height="${boxH}" rx="12"/>`);
  const stereotype=names[i][1]==='external'?'external system':names[i][1];
  add(`<text class="stereo" x="${xs[i]}" y="${topY+24}">«${esc(stereotype)}»</text>`);
  const label=names[i][0];
  if (label.length>27) {
    const split=label.indexOf(':')+1;
    add(`<text class="headText" x="${xs[i]}" y="${topY+49}"><tspan x="${xs[i]}">${esc(label.slice(0,split))}</tspan><tspan x="${xs[i]}" dy="20">${esc(label.slice(split))}</tspan></text>`);
  } else add(`<text class="headText" x="${xs[i]}" y="${topY+57}">${esc(label)}</text>`);
}

// Legend and note.
add(`<rect x="1680" y="3200" width="28" height="28" rx="6" fill="#d9ebff" stroke="#2867ad" stroke-width="2"/>`);
add(`<text class="legend" x="1720" y="3221">Blue - Alec (s4232003) - Teaching Staff: Grade Submission</text>`);
add(`<text class="legend" x="70" y="3221">Reference for recreation in Lucidchart - replace or retain boundary/control classes consistently in the consolidated class diagram.</text>`);
add(`</svg>`);

fs.writeFileSync('assignment3/grade-submission-sequence-HD-reference.svg', out.join('\n'));
