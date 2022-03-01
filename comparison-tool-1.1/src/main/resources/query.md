# Three test cases 

The function 'ccd_criminal_file_content.fcFileContentGet' shows we will implement three test cases

## getCriminalFileContentMDoc.csv
```
select distinct mdoc.mdoc_justin_no MdocJustinNo
    from justin_matter_docs mdoc,
    justin_participant_profiles prof
    where mdoc.mdoc_sworn_or_filed_yn = 'Y'
    and prof.mdoc_justin_no = mdoc.mdoc_justin_no
```

## getCriminalFileContentApprId.csv
```
select distinct
        appr.appr_id
    from justin_appearances appr,
         justin_appearance_counts appc,
         justin_mdpr_counts mdpr,
         justin_matter_doc_counts mdct,
         justin_matter_docs mdoc,
         justin_participant_profiles prof ,
         justin_physical_files phys,
         justin_appearances Aappr,
         justin_appearance_counts Aappc,
         justin_mdpr_counts Amdpr,
         justin_matter_doc_counts Amdct,
         justin_matter_docs Amdoc,
         justin_participant_profiles Aprof
   where 
      appc.appr_id = appr.appr_id 
     and nvl(appc.appc_cancelled_yn,'N') = 'N'
     and nvl(appc.appc_confirmed_yn,'Y') = 'Y' 
     and mdpr.mdct_justin_no = appc.mdpr_mdct_justin_no
     and mdpr.mdct_seq_no = appc.mdpr_mdct_seq_no
     and mdpr.mdpr_seq_no = appc.mdpr_seq_no
     and mdct.mdoc_justin_no = mdpr.mdct_justin_no
     and mdct.mdct_seq_no = mdpr.mdct_seq_no
     and prof.part_id = mdpr.prof_part_id
     and prof.prof_seq_no = mdpr.prof_seq_no
     and mdoc.mdoc_justin_no = prof.mdoc_justin_no 
     and phys.phys_id = mdoc.phys_id
     and Amdoc.phys_id = phys.phys_id
     and Aprof.mdoc_justin_no = Amdoc.mdoc_justin_no
     and Aprof.part_id = prof.part_id  
     and Amdct.mdoc_justin_no = Amdoc.mdoc_justin_no
     and Amdpr.prof_part_id = Aprof.part_id
     and Amdpr.prof_seq_no = Aprof.prof_seq_no
     and Aappc.mdpr_mdct_justin_no = Amdpr.mdct_justin_no
     and Aappc.mdpr_mdct_seq_no = Amdpr.mdct_seq_no
     and Aappc.mdpr_seq_no = Amdpr.mdpr_seq_no
     and nvl(Aappc.appc_cancelled_yn,'N') = 'N'
     and nvl(Aappc.appc_confirmed_yn,'Y') = 'Y' 
     and Aappr.appr_id = Aappc.appr_id
     FETCH FIRST 5000 ROWS ONLY
```
## getCriminalFileContentRoomCodeCompare
```
we use this selection statement 1) instead of 2) because the below could not output results and throw error 'ORA-01652: unable to extend temp segment by 128 in tablespace TEMP' due to system memory limitation                       

1) select distinct clst.ctrm_room_cd, TO_char(clst.clst_court_dt,'YYYY-MM-DD HH24:MI:SS') || '.0', agen.AGEN_AGENCY_IDENTIFIER_CD
     from justin_court_lists clst, JUSTIN.justin_agencies agen
     where clst.ctrm_agen_id = agen.agen_id
     FETCH FIRST 5000 ROWS ONLY

2) select distinct clst.clst_court_dt, clst.ctrm_agen_id, clst.ctrm_room_cd
    from justin_court_lists clst,
    justin_appearances appr,
    justin_appearance_counts appc,
    justin_mdpr_counts mdpr,
    justin_matter_doc_counts mdct,
    justin_matter_docs mdoc,
    justin_participant_profiles prof ,
    justin_physical_files phys,
    justin_appearances Aappr,
    justin_appearance_counts Aappc,
    justin_mdpr_counts Amdpr,
    justin_matter_doc_counts Amdct,
    justin_matter_docs Amdoc,
    justin_participant_profiles Aprof
    where
    appr.clst_id = clst.clst_id
    and appc.appr_id = appr.appr_id
    and nvl(appc.appc_cancelled_yn,'N') = 'N'
    and nvl(appc.appc_confirmed_yn,'Y') = 'Y'
    and mdpr.mdct_justin_no = appc.mdpr_mdct_justin_no
    and mdpr.mdct_seq_no = appc.mdpr_mdct_seq_no
    and mdpr.mdpr_seq_no = appc.mdpr_seq_no
    and mdct.mdoc_justin_no = mdpr.mdct_justin_no
    and mdct.mdct_seq_no = mdpr.mdct_seq_no
    and prof.part_id = mdpr.prof_part_id
    and prof.prof_seq_no = mdpr.prof_seq_no
    and mdoc.mdoc_justin_no = prof.mdoc_justin_no
    and phys.phys_id = mdoc.phys_id
    and Amdoc.phys_id = phys.phys_id
    and Aprof.mdoc_justin_no = Amdoc.mdoc_justin_no
    and Aprof.part_id = prof.part_id  
    and Amdct.mdoc_justin_no = Amdoc.mdoc_justin_no
    and Amdpr.prof_part_id = Aprof.part_id
    and Amdpr.prof_seq_no = Aprof.prof_seq_no
    and Aappc.mdpr_mdct_justin_no = Amdpr.mdct_justin_no
    and Aappc.mdpr_mdct_seq_no = Amdpr.mdct_seq_no
    and Aappc.mdpr_seq_no = Amdpr.mdpr_seq_no
    and nvl(Aappc.appc_cancelled_yn,'N') = 'N'
    and nvl(Aappc.appc_confirmed_yn,'Y') = 'Y'
    and Aappr.appr_id = Aappc.appr_id 
```